/*
 *  Copyright 2011-2012 SERLI (www.serli.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.serli.chell.framework.form;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.util.SingletonFactory;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.converter.CheckboxFieldConverter;
import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.form.converter.IdentityFieldConverter;
import com.serli.chell.framework.form.converter.TabFieldConverter;
import com.serli.chell.framework.form.converter.FieldConverterFactory;
import com.serli.chell.framework.loader.DataLoader;
import com.serli.chell.framework.loader.DataLoader.EmptyDataLoader;
import com.serli.chell.framework.message.Message;
import com.serli.chell.framework.message.MessageAnnotationHelper;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.reflect.AnnotationMetaDataInfos;
import com.serli.chell.framework.upload.UploadHandler;
import com.serli.chell.framework.util.ClassUtils;
import com.serli.chell.framework.util.ClassUtils.ArgumentType;
import com.serli.chell.framework.validation.Constraint;
import com.serli.chell.framework.validation.TabConstraint;
import com.serli.chell.framework.validation.annotation.Required;
import com.serli.chell.framework.validation.check.NotRequiredCheck;
import com.serli.chell.framework.validation.check.NotRequiredTabCheck;
import com.serli.chell.framework.validation.check.RequiredCheck;
import com.serli.chell.framework.validation.check.RequiredTabCheck;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FormField implements Comparable<FormField> {

    private Field field;
    private FormType type;
    private Message label;
    private String cssClass;
    private String htmlId;
    private String preferenceName;
    private boolean multiValued;
    private boolean required;
    private int order;
    private int size;
    private int rows;
    private int cols;
    private List<Constraint<?>> constraints;
    private DataLoader dataLoader;
    private FieldConverter converter;
    private UploadHandler uploadHandler;

    public FormField(Field field, boolean multiValued, boolean required) {
        this.field = field;
        this.field.setAccessible(true);
        this.label = Message.withText(field.getName());
        this.constraints = new ArrayList<Constraint<?>>();
        this.multiValued = multiValued;
        this.required = required;
        this.dataLoader = null;
        this.cssClass = null;
        this.htmlId = null;
        this.uploadHandler = null;
        this.preferenceName = null;
        this.converter = IdentityFieldConverter.INSTANCE;
        this.type = FormType.TEXT;
        this.size = Constant.AUTO;
        this.rows = Constant.UNSPECIFIED;
        this.cols = Constant.UNSPECIFIED;
        this.order = Constant.DEFAULT_ORDER;
        addRequiredConstraint();
    }

    void associateInputAnnotation(FormType inputType, Annotation annotation, AnnotationMetaDataInfos annotationInfos) {
        Integer ival;
        String sval;
        type = inputType;
        label = MessageAnnotationHelper.getMessage(annotation, field.getName());

        if ((ival = (Integer) annotationInfos.getValue(Type.HTML_SIZE, annotation)) != null) {
            size = ival;
        }
        if ((ival = (Integer) annotationInfos.getValue(Type.HTML_ROWS, annotation)) != null) {
            rows = ival;
        }
        if ((ival = (Integer) annotationInfos.getValue(Type.HTML_COLS, annotation)) != null) {
            cols = ival;
            if ((FormType.RADIO.equals(inputType) || FormType.CHECKBOX.equals(inputType)) && cols <= 0) {
                cols = 1;
            }
        }
        if ((ival = (Integer) annotationInfos.getValue(Type.FIELD_ORDER, annotation)) != null) {
            order = ival;
        }
        if ((sval = (String) annotationInfos.getValue(Type.HTML_CSS_CLASS, annotation)) != null && sval.length() > 0) {
            cssClass = sval;
        }
        if ((sval = (String) annotationInfos.getValue(Type.HTML_ID, annotation)) != null && sval.length() > 0) {
            htmlId = sval;
        }
        if ((sval = (String) annotationInfos.getValue(Type.PREFERENCE_NAME, annotation)) != null && sval.length() > 0) {
            preferenceName = sval;
        }
        Class<? extends DataLoader> dataLoaderClass = (Class<? extends DataLoader>) annotationInfos.getValue(Type.FIELD_DATA_LOADER, annotation);
        if (dataLoaderClass != null) {
            dataLoader = SingletonFactory.get(dataLoaderClass);
        }
        Class<? extends UploadHandler> uploadHandlerClass = (Class<? extends UploadHandler>) annotationInfos.getValue(Type.FIELD_FILE_UPLOAD_HANDLER, annotation);
        if (uploadHandlerClass != null) {
            uploadHandler = SingletonFactory.get(uploadHandlerClass);
            uploadHandler.configure();
            setSmartConverter(uploadHandler.getConverter());
        }
        if (multiValued && (FormType.TEXT.equals(type) || FormType.TEXTAREA.equals(type) || FormType.PASSWORD.equals(type) ||
            FormType.HIDDEN.equals(type) || FormType.FILE.equals(type) || FormType.RADIO.equals(type))) {
            throw new ChellException("Inputs of type TEXT, TEXTAREA, PASSWORD, HIDDEN, FILE or RADIO can not be multi-valued.");
        } else if ((FormType.RADIO.equals(type) || FormType.SELECT.equals(type)) && dataLoader.getClass().equals(EmptyDataLoader.class)) {
            throw new ChellException("Input of type SELECT or RADIO must have a data loader.");
        } else if (!multiValued && FormType.CHECKBOX.equals(type)) {
            if (required) {
                throw new ChellException("Single input of type CHECKBOX can not be required.");
            } else {
                setConverter(CheckboxFieldConverter.class);
            }
        }
        Class<? extends FieldConverter> converterClass = (Class<? extends FieldConverter>) annotationInfos.getValue(Type.FIELD_CONVERTER, annotation);
        if (converterClass != null && !IdentityFieldConverter.class.equals(converterClass)) {
            setSmartConverter(SingletonFactory.get(converterClass), false);
        }
    }

    public Field getField() {
        return field;
    }

    public String getLabel() {
        return label.get();
    }

    public String getLabel(Locale locale) {
        return label.get(locale);
    }

    public List<Constraint<?>> getConstraints() {
        return constraints;
    }

    public void addConstraint(Constraint<?> constraint) {
        testConstraintValidity(constraint);
        constraints.add(constraint);
    }

    public void addSmartConstraint(Constraint<?> constraint) {
        ArgumentType at = testConstraintValidity(constraint);
        if (multiValued) {
            if (at.isArray()) {
                constraints.add(constraint);
            } else {
                constraints.add(new TabConstraint((Constraint<String>) constraint));
            }
        } else if (!at.isArray()) {
            constraints.add(constraint);
        } else {
            throw new ChellException("Can not add multi-valued constraint on a no multi-valued field.");
        }
    }

    private ArgumentType testConstraintValidity(Constraint<?> constraint) {
        ArgumentType at = ClassUtils.getGenericArgumentType(constraint.getClass(), Constraint.class, 0);
        if (!String.class.equals(at.getType())) {
            throw new ChellException("Custom constraints must implements Constraint<String> or Constraint<String[]>.");
        }
        return at;
    }

    private void addRequiredConstraint() {
        if (required) {
            Required annotation = field.getAnnotation(Required.class);
            if (annotation == null) {
                addConstraint(multiValued ? RequiredTabCheck.DEFAULT_INSTANCE : RequiredCheck.DEFAULT_INSTANCE);
            } else {
                addConstraint(multiValued ? new RequiredTabCheck(annotation) : new RequiredCheck(annotation));
            }
        } else {
            addConstraint(multiValued ? NotRequiredTabCheck.DEFAULT_INSTANCE : NotRequiredCheck.DEFAULT_INSTANCE);
        }
    }

    public FieldConverter getConverter() {
        return converter;
    }

    public void setConverter(FieldConverter newConverter) {
        if (newConverter == null) {
            newConverter = IdentityFieldConverter.INSTANCE;
        }
        testConverterValidity(newConverter);
        converter = newConverter;
    }

    public void setConverter(Class<? extends FieldConverter> converterClass) {
        setConverter(SingletonFactory.get(converterClass));
    }

    public void setSmartConverter(FieldConverter converter) {
        setSmartConverter(converter, true);
    }

    public void setSmartConverter(Class<? extends FieldConverter> converterClass) {
        FieldConverter fieldConverter = SingletonFactory.get(converterClass);
        setSmartConverter(fieldConverter, false);
    }

    private void setSmartConverter(FieldConverter newConverter, boolean instantiate) {
        if (newConverter == null) {
            converter = IdentityFieldConverter.INSTANCE;
        } else {
            ArgumentType at = testConverterValidity(newConverter);
            if (multiValued) {
                if (at.isArray()) {
                    converter = newConverter;
                } else {
                    if (instantiate) {
                        converter = new TabFieldConverter(newConverter);
                    } else {
                        converter = FieldConverterFactory.getTabConverter(newConverter);
                    }
                }
            } else if (!at.isArray()) {
                converter = newConverter;
            } else {
                throw new ChellException(signature() + "Can not add multi-valued converter on a no multi-valued field.");
            }
        }
    }

    private ArgumentType testConverterValidity(FieldConverter newConverter) {
        Class cvtClass = converter.getClass();
        if (!IdentityFieldConverter.class.equals(cvtClass) && !cvtClass.equals(newConverter.getClass())) {
            throw new ChellException(signature() + "Field has already a registred converter of class " + cvtClass.getName() +
                                     ". Can not register converter of class " + newConverter.getClass().getName());
        } else {
            ArgumentType at = ClassUtils.getGenericArgumentType(newConverter.getClass(), FieldConverter.class, 1);
            if (!String.class.equals(at.getType())) {
                throw new ChellException(signature() + "Custom converters must implements FieldConverter<?, String> or FieldConverter<?, String[]>.");
            }
            return at;
        }
    }

    public int getOrder() {
        return order;
    }

    public boolean isRequired() {
        return required;
    }

    public boolean isMultiValued() {
        return multiValued;
    }

    public boolean isUploadField() {
        return FormType.FILE.equals(type);
    }

    public String getPreferenceName() {
        return preferenceName;
    }

    public String getName() {
        return field.getName();
    }

    public <T extends Form> Class<T> getFormClass() {
        return (Class<T>) field.getDeclaringClass();
    }

    public void setValue(Form form, Object value) {
        try {
            field.set(form, value);
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public String getValue(Form form) {
        try {
            return (String) field.get(form);
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public String[] getValues(Form form) {
        try {
            return (String[]) field.get(form);
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public Object getValueObject(Form form) {
        try {
            return field.get(form);
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public String[] getValueArray(Object form) {
        try {
            if (multiValued) {
                return (String[]) field.get(form);
            } else {
                String value = (String) field.get(form);
                return new String[] { value };
            }
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public Set<String> getValueSet(Form form) {
        try {
            if (multiValued) {
                String[] values = (String[]) field.get(form);
                if (values != null) {
                    Set<String> result = new HashSet<String>(values.length);
                    for (String value : values) {
                        result.add(value);
                    }
                    return result;
                }
            } else {
                String value = (String) field.get(form);
                if (value != null) {
                    Set<String> result = new HashSet<String>(1);
                    result.add(value);
                    return result;
                }
            }
            return new HashSet<String>(0);
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public void setConvertedValue(Form form, Object convertedValue) {
        if (convertedValue == null) {
            setValue(form, null);
        } else {
            setValue(form, converter.fromObject(convertedValue));
        }
    }

    public Object getConvertedValue(Form form) {
        if (multiValued) {
            String[] values = getValues(form);
            if (values != null) {
                return converter.toObject(values);
            }
        } else {
            String value = getValue(form);
            if (value != null && value.length() > 0) {
                return converter.toObject(value);
            }
        }
        return null;
    }

    public String getHtmlId() {
        return htmlId;
    }

    public String getCssClass() {
        return cssClass;
    }

    public int getSize() {
        return size;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getMaxLength() {
        if (rows != Constant.UNSPECIFIED) {
            return rows;
        } else {
            return size;
        }
    }

    public FormType getType() {
        return type;
    }

    public UploadHandler getUploadHandler() {
        return uploadHandler;
    }

    /**
     * Can be only used by com.serli.chell.framework.form.Form class.
     */
    protected DataLoader getDataLoader() {
        return dataLoader;
    }

    public int compareTo(FormField other) {
        return order - other.order;
    }

    private String signature() {
        StringBuilder b = new StringBuilder();
        b.append(getFormClass().getSimpleName());
        b.append("['").append(getName()).append("'] : ");
        return b.toString();
    }


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(field.getName()).append(" [");
        b.append("type = ").append(type);
        b.append(", required = ").append(required);
        b.append(", multi-valued = ").append(multiValued).append(']');
        return b.toString();
    }
}
