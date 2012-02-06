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
import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.Render;
import com.serli.chell.framework.Scope;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.PhaseType;
import com.serli.chell.framework.form.annotation.HtmlButtonSubmit;
import com.serli.chell.framework.form.render.FormRenderer;
import com.serli.chell.framework.form.render.el.ButtonRenderer;
import com.serli.chell.framework.form.render.el.ErrorFieldRenderer;
import com.serli.chell.framework.form.render.el.FieldConverterAccessor;
import com.serli.chell.framework.form.render.el.InputFieldRenderer;
import com.serli.chell.framework.form.render.el.LabelFieldRenderer;
import com.serli.chell.framework.form.render.el.UploadFilenameAccessor;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.message.MessageType;
import com.serli.chell.framework.loader.DataModel;
import com.serli.chell.framework.store.Store;
import com.serli.chell.framework.upload.FileUploadStatus;
import com.serli.chell.framework.validation.ValidationException;
import com.serli.chell.framework.validation.Constraint;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.portlet.ActionRequest;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
// TODO : Ajouter la notion de View (un Form est une View qui ajoute des notions de validation)
@HtmlButtonSubmit
public abstract class Form implements Serializable {

    private static final long serialVersionUID = -3256476494742474699L;

    public static final String ON = "on";
    public static final String OFF = null;

    private String namespace;
    private FormStructure structure;
    private Map<String, String> errorMap = new LinkedHashMap<String, String>();
    private Map<String, List<DataModel>> dataModels = new HashMap<String, List<DataModel>>();

    private ButtonRenderer buttonRenderer;
    private InputFieldRenderer inputRenderer;
    private ErrorFieldRenderer errorRenderer;
    private LabelFieldRenderer labelRenderer;
    private FieldConverterAccessor fieldConverterAccessor;
    private UploadFilenameAccessor uploadFileNameAccessor;

    public Form(String namespace) {
        this.namespace = namespace;
        this.structure = FormStructureRegistry.getFormStructure(getClass());
        this.buttonRenderer = new ButtonRenderer(this);
        this.inputRenderer = new InputFieldRenderer(this);
        this.errorRenderer = new ErrorFieldRenderer(this);
        this.labelRenderer = new LabelFieldRenderer(this);
        this.fieldConverterAccessor = new FieldConverterAccessor(this);
        this.uploadFileNameAccessor = new UploadFilenameAccessor(this);
    }

    // TODO : Appeler le minimum de fois les loader de donnees.
    public void copyData(Form other) {
        this.dataModels = other.dataModels;
    }

    public void fillFromRequest() {
        PortletRequest request = PortletHelper.getRequest();
        PhaseType phase = PortletHelper.getPhase();
        if (PhaseType.ACTION.equals(phase)) {
            ActionRequest actionRequest = (ActionRequest) request;
            if (FileUploadStatus.isUploadForm(actionRequest)) {
                if (structure.isUploadForm()) {
                    fillFromMultiPartRequest(actionRequest);
                } else {
                    throw new ChellException("HTML form '" + getClass().getName() + "' must have 'application/x-www-form-urlencoded' enctype.");
                }
            } else if (structure.isUploadForm()) {
                    throw new ChellException("HTML form '" + getClass().getName() + "' must have 'multipart/form-data' enctype.");
            } else {
                fillFromUrlEncodedRequest(request);
            }
        } else {
            fillFromUrlEncodedRequest(request);
        }
    }

    private void fillFromUrlEncodedRequest(PortletRequest request) {
        String value;
        String[] values;
        for (FormField field : getFields()) {
            if (field.isMultiValued()) {
                values = request.getParameterValues(field.getName());
                field.setValue(this, (values == null ? Constant.EMPTY_TAB : values));
            } else {
                value = request.getParameter(field.getName());
                field.setValue(this, (value == null ? Constant.EMPTY : value));
            }
        }
    }

    private void fillFromMultiPartRequest(ActionRequest request) {
        Map<String, String[]> preferences = PortletHelper.getPreferences().getMap();
        FileUploadStatus fus = FileUploadStatus.fromRequest(request, structure);
        for (FormField field : getFields()) {
            if (fus.isUnchangedUploadParameter(field)) {
                fillFieldFromPreferences(field, preferences);
            } else {
                if (field.isMultiValued()) {
                    field.setValue(this, fus.getParameters(field, getFieldValuesFromPreferences(field, preferences)));
                } else {
                    field.setValue(this, fus.getParameter(field, getFieldValueFromPreferences(field, preferences)));
                }
            }
        }
        fus.store();
    }

    public void fillFromPreferences() {
        Map<String, String[]> preferences = PortletHelper.getPreferences().getMap();
        for (FormField field : getFields()) {
            fillFieldFromPreferences(field, preferences);
        }
    }

    private void fillFieldFromPreferences(FormField field, Map<String, String[]> preferences) {
        String[] values;
        String preferenceName = getPreferenceName(field);
        if (field.isMultiValued()) {
            values = preferences.get(preferenceName);
            if (values != null) {
                field.setValue(this, values);
            }
        } else {
            values = preferences.get(preferenceName);
            if (values != null && values.length > 0) {
                field.setValue(this, values[0]);
            }
        }
    }

    private String getFieldValueFromPreferences(FormField field, Map<String, String[]> preferences) {
        String[] values = preferences.get(getPreferenceName(field));
        if (values != null && values.length > 0) {
            return values[0];
        }
        return Constant.EMPTY;
    }

    private String[] getFieldValuesFromPreferences(FormField field, Map<String, String[]> preferences) {
        String[] values = preferences.get(getPreferenceName(field));
        return (values == null ? Constant.EMPTY_TAB : values);
    }

    public void saveInPreferences() {
        PortletPreferences preferences = PortletHelper.getPreferences();
        FileUploadStatus fus = FileUploadStatus.current();
        try {
            String preferenceName;
            for (FormField field : getFields()) {
                preferenceName = getPreferenceName(field);
                if (field.isMultiValued()) {
                    preferences.setValues(preferenceName, field.getValues(this));
                } else {
                    preferences.setValue(preferenceName, field.getValue(this));
                }
            }
            preferences.store();
            fus.discardFormUploadedItems(this);
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public void resetPreferences() {
        // TODO : PRIORITAIRE : Gestion des champs uploades
        PortletPreferences preferences = PortletHelper.getPreferences();
        try {
            for (FormField field : getFields()) {
                preferences.reset(getPreferenceName(field));
            }
            preferences.store();
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public boolean validate() {
        return validate(true);
    }

    public boolean validate(boolean withSuccessMessage) {
        String fieldName;
        Object fieldValue;
        clearErrors();
        FileUploadStatus fus = FileUploadStatus.current();
        for (FormField field : getFields()) {
            fieldName = field.getName();
            fieldValue = field.getValueObject(this);
            try {
                if (field.isUploadField()) {
                    propageUploadErrors(fus, fieldName);
                }
                for (Constraint constraint : field.getConstraints()) {
                    constraint.validateField(this, field, fieldValue);
                }
            } catch (ValidationException ex) {
            }
        }
        if (!hasErrors()) {
            doValidation();
        }
        Store sessionStore = Scope.session();
        if (!hasErrors()) {
            if (withSuccessMessage) {
                Render.msgKey(MessageType.SUCCESS, MessageKey.MESSAGE_PREFERENCES_SAVED);
            }
            FormHelper.removeStoredForm(sessionStore, this);
            return true;
        } else {
            FormHelper.storeForm(sessionStore, this);
            return false;
        }
    }

    private void propageUploadErrors(FileUploadStatus fus, String fieldName) throws ValidationException {
        if (fus != null) {
            String errorMsg = fus.getUploadError(fieldName);
            if (errorMsg != null) {
                addError(fieldName, errorMsg);
                throw new ValidationException();
            }
        }
    }

    /**
     * Override this method for specific validation.
     */
    protected void doValidation() {
    }

    public boolean hasErrors() {
        return (errorMap.size() > 0);
    }

    public int countErrors() {
        return errorMap.size();
    }

    public void clearErrors() {
        errorRenderer.clear();
        errorMap.clear();
    }

    public void addError(String fieldName, String message) {
        errorMap.put(fieldName, message);
    }

    public List<DataModel> getData(String fieldName) {
        List<DataModel> data = dataModels.get(fieldName);
        if (data == null) {
            data = getField(fieldName).getDataLoader().getData();
            if (data == null) {
                data = new ArrayList<DataModel>();
            }
            dataModels.put(fieldName, data);
        }
        return data;
    }

    public Collection<FormField> getFields() {
        return structure.getFields();
    }

    public Set<String> getFieldNames() {
        return structure.getFieldNames();
    }

    public FormField getField(String fieldName) {
        FormField formField = structure.getField(fieldName);
        if (formField == null) {
            throw new ChellException("Field '" + fieldName + "' is not found in form : " + getClass().getName());
        }
        return formField;
    }

    public String getFieldValue(String fieldName) {
        return getField(fieldName).getValue(this);
    }

    public String[] getFieldValues(String fieldName) {
        return getField(fieldName).getValueArray(this);
    }

    public String getValueLabel(String fieldName, String value) {
        if (value != null) {
            List<DataModel> data = getData(fieldName);
            if (data != null) {
                for (DataModel dm : data) {
                    if (value.equals(dm.getValue())) {
                        return dm.getLabel();
                    }
                }
            }
        }
        return null;
    }

    public String[] getValueLabels(String fieldName, String... values) {
        if (values != null) {
            String[] result = new String[values.length];
            List<DataModel> data = getData(fieldName);
            String value;
            for (int i = 0; i < result.length; i++) {
                value = values[i];
                result[i] = null;
                if (data != null && values[i] != null) {
                    for (DataModel dm : data) {
                        if (value.equals(dm.getValue())) {
                            result[i] = dm.getLabel();
                            break;
                        }
                    }
                }
            }
            return result;
        } else {
            return new String[0];
        }
    }

    public String getCurrentValueLabel(String fieldName) {
        return getValueLabel(fieldName, getField(fieldName).getValue(this));
    }

    public String[] getCurrentValueLabels(String fieldName) {
        return getValueLabels(fieldName, getField(fieldName).getValues(this));
    }

    public String getFieldLabel(String fieldName) {
        return getField(fieldName).getLabel();
    }

    public void set(String fieldName, Object convertedValue) {
        fieldConverterAccessor.put(fieldName, convertedValue);
    }

    public Object get(String fieldName) {
        return fieldConverterAccessor.get(fieldName);
    }

    public <T> T get(String fieldName, Class<T> convertionClass) {
        return (T) fieldConverterAccessor.get(fieldName);
    }

    public Boolean getAsBoolean(String fieldName) {
        return (Boolean) fieldConverterAccessor.get(fieldName);
    }

    public Integer getAsInteger(String fieldName) {
        return (Integer) fieldConverterAccessor.get(fieldName);
    }

    public Integer[] getAsIntegers(String fieldName) {
        return (Integer[]) fieldConverterAccessor.get(fieldName);
    }

    public Long getAsLong(String fieldName) {
        return (Long) fieldConverterAccessor.get(fieldName);
    }

    public Long[] getAsLongs(String fieldName) {
        return (Long[]) fieldConverterAccessor.get(fieldName);
    }

    public Double getAsDouble(String fieldName) {
        return (Double) fieldConverterAccessor.get(fieldName);
    }

    public Double[] getAsDoubles(String fieldName) {
        return (Double[]) fieldConverterAccessor.get(fieldName);
    }

    public Date getAsDate(String fieldName) {
        return (Date) fieldConverterAccessor.get(fieldName);
    }

    public Date[] getAsDates(String fieldName) {
        return (Date[]) fieldConverterAccessor.get(fieldName);
    }

    public File getAsFile(String fieldName) {
        return (File) fieldConverterAccessor.get(fieldName);
    }

    public boolean getAsBool(String fieldName) {
        return getAsBool(fieldName, false);
    }

    public boolean getAsBool(String fieldName, boolean defaultValue) {
        Boolean value = (Boolean) fieldConverterAccessor.get(fieldName);
        if (value != null) {
            return value.booleanValue();
        }
        return defaultValue;
    }

    public int getAsInt(String fieldName) {
        return getAsInt(fieldName, 0);
    }

    public int getAsInt(String fieldName, int defaultValue) {
        Integer value = (Integer) fieldConverterAccessor.get(fieldName);
        if (value != null) {
            return value.intValue();
        }
        return defaultValue;
    }

    public double getAsFloat(String fieldName) {
        return getAsFloat(fieldName, 0.0);
    }

    public double getAsFloat(String fieldName, double defaultValue) {
        Double value = (Double) fieldConverterAccessor.get(fieldName);
        if (value != null) {
            return value.doubleValue();
        }
        return defaultValue;
    }

    public FormStructure getStructure() {
        return structure;
    }

    public FormRenderer getRenderer() {
        return structure.getRenderer();
    }

    public final String getNamespace() {
        return namespace;
    }

    public String getNamespaceKey(FormField field) {
        StringBuilder b = new StringBuilder();
        b.append(namespace);
        String fieldName = field.getName();
        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        b.append(fieldName);
        return b.toString();
    }

    public String getPreferenceName(FormField field) {
        String preferenceName = field.getPreferenceName();
        if (preferenceName != null && preferenceName.length() > 0) {
            return preferenceName;
        } else {
            return getNamespaceKey(field);
        }
    }

    /* --------------------------------------------------
     *    Accessibles fields with EL while rendering
     * --------------------------------------------------
     */

    public final Map<String, List<DataModel>> getData() {
        return dataModels;
    }

    public final Map<String, String> getError() {
        return errorMap;
    }

    public final String getHtml() {
        return structure.getRenderer().renderForm(this);
    }

    public final InputFieldRenderer getInput() {
        return inputRenderer;
    }

    public final ErrorFieldRenderer getErrors() {
        return errorRenderer;
    }

    public final ButtonRenderer getButton() {
        return buttonRenderer;
    }

    public final LabelFieldRenderer getLabel() {
        return labelRenderer;
    }

    public final FieldConverterAccessor getConvert() {
        return fieldConverterAccessor;
    }

    // TODO : Laisser ?
    public final UploadFilenameAccessor getUploadFileName() {
        return uploadFileNameAccessor;
    }
}
