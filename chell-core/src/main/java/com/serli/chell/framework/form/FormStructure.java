package com.serli.chell.framework.form;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.FormFieldConfiguration.InputConfiguration;
import com.serli.chell.framework.form.FormFieldConfiguration.ValidationConfiguration;
import com.serli.chell.framework.form.FormFieldConfiguration.NoConfiguration;
import com.serli.chell.framework.form.annotation.HtmlTransient;
import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.form.converter.IdentityFieldConverter;
import com.serli.chell.framework.form.render.FormRenderer;
import com.serli.chell.framework.form.render.TableFormRenderer;
import com.serli.chell.framework.message.Message;
import com.serli.chell.framework.message.MessageAnnotationHelper;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.reflect.AnnotationMetaDataInfos;
import com.serli.chell.framework.resource.ResourceElement;
import com.serli.chell.framework.resource.ResourceProvider;
import com.serli.chell.framework.util.ClassUtils;
import com.serli.chell.framework.util.SingletonFactory;
import com.serli.chell.framework.validation.Constraint;
import com.serli.chell.framework.validation.NoConstraint;
import com.serli.chell.framework.validation.annotation.Required;
import com.serli.chell.framework.validation.annotation.Length;
import com.serli.chell.framework.validation.annotation.Size;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FormStructure implements Serializable {

    private static final Log LOGGER = LogFactory.getLog(FormStructure.class);

    private static final Map<Class<? extends Annotation>, FormFieldConfiguration> FORM_FIELD_CONFIGURATION = new HashMap();

    private Map<String, FormField> fields = new LinkedHashMap<String, FormField>();
    private Map<String, FormButton> buttons = new LinkedHashMap<String, FormButton>();
    private Set<ResourceElement> resources = new LinkedHashSet<ResourceElement>();
    private FormRenderer renderer = SingletonFactory.get(TableFormRenderer.class);
    private Message label = Message.empty();
    private String cssClass = null;
    private String htmlId = null;
    private String requiredHtml = Constant.HTML_REQUIRED;
    private String separatorHtml = Constant.HTML_FIELD_SEPARATOR;
    private long maxUploadFileSize = Constant.UNSPECIFIED;
    private boolean uploadForm = false;

    public FormStructure(Class<? extends Form> formClass) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("Loading form : " + formClass.getName());
        }
        loadForm(formClass);
        loadFields(formClass);
    }

    private void loadForm(Class<? extends Form> formClass) {
        FormType formType;
        AnnotationMetaDataInfos annotationInfos;
        Class<?> currentClass = formClass;
        List<FormButton> buttonList = new ArrayList<FormButton>();
        boolean sortButtons = false;
        do {
            ResourceProvider.loadResourcesFromClass(resources, currentClass);
            for (Annotation annotation : currentClass.getDeclaredAnnotations()) {
                ResourceProvider.loadResourcesFromAnnotation(resources, annotation);
                annotationInfos = AnnotationMetaDataInfos.get(annotation.annotationType());
                formType = (FormType) annotationInfos.getValue(Type.FORM_TYPE, annotation);
                if (formType != null) {
                    switch (formType) {
                        case FORM: loadForm(annotationInfos, annotation); break;
                        case BUTTON: sortButtons |= loadButton(annotationInfos, annotation, buttonList); break;
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        } while (!currentClass.equals(Object.class));

        if (sortButtons) {
            buttons.clear();
            Collections.sort(buttonList);
            for (FormButton button : buttonList) {
                buttons.put(button.getName(), button);
            }
        }
    }

    private void loadForm(AnnotationMetaDataInfos annotationInfos, Annotation annotation) {
        String value;
        label = MessageAnnotationHelper.getMessage(annotation);

        if ((value = (String) annotationInfos.getValue(Type.FORM_REQUIRED_TEXT, annotation)) != null) {
            requiredHtml = value;
        }
        if ((value = (String) annotationInfos.getValue(Type.FORM_SEPARATOR_TEXT, annotation)) != null) {
            separatorHtml = value;
        }
        if ((value = (String) annotationInfos.getValue(Type.HTML_CSS_CLASS, annotation)) != null && value.length() > 0) {
            cssClass = value;
        }
        if ((value = (String) annotationInfos.getValue(Type.HTML_ID, annotation)) != null && value.length() > 0) {
            htmlId = value;
        }
        Class<? extends FormRenderer> rendererClass = (Class<? extends FormRenderer>) annotationInfos.getValue(Type.FORM_RENDERER, annotation);
        if (rendererClass != null) {
            renderer = SingletonFactory.get(rendererClass);
        } else {
            throw new ChellException("@AnnotationMetaData(Type.FORM_RENDERER) not found on interface " + annotation.annotationType().getName());
        }
    }

    private boolean loadButton(AnnotationMetaDataInfos annotationInfos, Annotation annotation, List<FormButton> buttonList) {
        FormButton button = FormButton.createFromAnnotation(annotationInfos, annotation);
        String buttonName = button.getName();
        if (!buttons.containsKey(buttonName)) {
            buttonList.add(button);
            buttons.put(buttonName, button);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Loading button : " + button);
            }
            return button.hasDefinedOrder();
        }
        return false;
    }

    private void loadFields(Class<? extends Form> formClass) {
        List<FormField> fieldList = new ArrayList<FormField>();
        Class<?> currentClass = formClass;
        Class<?> fieldType;
        FormField formField;
        FormFieldConfiguration ffc;
        boolean multiValued;
        boolean required;
        long maxFileSize;
        do {
            for (Field field : currentClass.getDeclaredFields()) {
                fieldType = field.getType();
                if (isFormField(field, fieldType)) {
                    multiValued = isMultivaluedField(fieldType);
                    required = isRequiredField(field, multiValued);
                    formField = new FormField(field, multiValued, required);
                    fieldList.add(formField);
                    for (Annotation annotation : field.getAnnotations()) {
                        ResourceProvider.loadResourcesFromAnnotation(resources, annotation);
                        ffc = getFieldConfiguration(annotation);
                        ffc.configure(formField, annotation);
                    }
                    if (formField.isUploadField()) {
                        uploadForm = true;
                        maxFileSize = formField.getUploadHandler().getMaxFileSize();
                        if (maxFileSize > maxUploadFileSize) {
                            maxUploadFileSize = maxFileSize;
                        }
                    }
                    if (LOGGER.isInfoEnabled()) {
                        LOGGER.info("Loading field : " + formField);
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        } while (!currentClass.equals(Form.class));
        Collections.sort(fieldList);
        for (FormField field : fieldList) {
            fields.put(field.getName(), field);
        }
    }

    private static synchronized FormFieldConfiguration getFieldConfiguration(Annotation annotation) {
        Class<? extends Annotation> annotationType = annotation.annotationType();
        FormFieldConfiguration ffc = FORM_FIELD_CONFIGURATION.get(annotationType);
        if (ffc == null) {
            FormFieldMetadata fieldMetadata = annotationType.getAnnotation(FormFieldMetadata.class);
            if (fieldMetadata != null) {
                Class<? extends FormFieldConfiguration> ffcClass = fieldMetadata.configuration();
                if (!NoConfiguration.class.equals(ffcClass)) {
                    ffc = SingletonFactory.get(ffcClass);
                } else {
                    Class<? extends FieldConverter> converterClass = fieldMetadata.converter();
                    Class<? extends Constraint> constraintClass = fieldMetadata.constraint();
                    if (IdentityFieldConverter.class.equals(converterClass)) {
                        converterClass = null;
                    }
                    if (NoConstraint.class.equals(constraintClass)) {
                        constraintClass = null;
                    }
                    ffc = new ValidationConfiguration(converterClass, constraintClass);
                }
            } else {
                FormInputMetadata inputMetaData = annotationType.getAnnotation(FormInputMetadata.class);
                if (inputMetaData != null) {
                    Class<? extends InputConfiguration> configurationClass = inputMetaData.configuration();
                    InputConfiguration ic = ClassUtils.newInstance(configurationClass);
                    ffc = ic.initialize(inputMetaData, annotationType);
                } else {
                    ffc = NoConfiguration.INSTANCE;
                }
            }
            FORM_FIELD_CONFIGURATION.put(annotationType, ffc);
        }
        return ffc;
    }

    private static boolean isFormField(Field field, Class<?> fieldType) {
        return ((field.getModifiers() & (Modifier.STATIC | Modifier.FINAL)) == 0) &&
                (fieldType.equals(String.class) || fieldType.equals(String[].class) &&
                !field.isAnnotationPresent(HtmlTransient.class));
    }

    private static boolean isMultivaluedField(Class<?> fieldType) {
        return fieldType.equals(String[].class);
    }

    private static boolean isRequiredField(Field field, boolean multiValued) {
        if (field.getAnnotation(Required.class) != null) {
            return true;
        } else if (multiValued) {
            Size annotation = field.getAnnotation(Size.class);
            return (annotation != null && (annotation.min() > 0 || annotation.value() > 0));
        } else {
            Length annotation = field.getAnnotation(Length.class);
            return (annotation != null && (annotation.min() > 0 || annotation.value() > 0));
        }
    }

    public String getLabel() {
        return label.get();
    }

    public String getLabel(Locale locale) {
        return label.get(locale);
    }

    public String getCssClass() {
        return cssClass;
    }

    public String getHtmlId() {
        return htmlId;
    }

    public FormRenderer getRenderer() {
        return renderer;
    }

    public Set<ResourceElement> getResources() {
        return resources;
    }

    public String getRequiredHtml() {
        return requiredHtml;
    }

    public String getSeparatorHtml() {
        return separatorHtml;
    }

    public long getMaxUploadFileSize() {
        return maxUploadFileSize;
    }

    public FormButton getButton(String name) {
        return buttons.get(name);
    }

    public Collection<FormButton> getButtons() {
        return buttons.values();
    }

    public boolean hasLegend() {
        return !label.isEmpty();
    }

    public Set<String> getFieldNames() {
        return fields.keySet();
    }

    public Collection<FormField> getFields() {
        return fields.values();
    }

    public FormField getField(String fieldName) {
        return fields.get(fieldName);
    }

    public boolean isUploadForm() {
        return uploadForm;
    }
}
