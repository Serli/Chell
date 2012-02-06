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

package com.serli.chell.framework.form.render;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormButton;
import com.serli.chell.framework.form.FormField;
import com.serli.chell.framework.form.FormStructure;
import com.serli.chell.framework.loader.DataModel;
import com.serli.chell.framework.message.MessageBundle;
import com.serli.chell.framework.message.MessageKey;
import com.serli.chell.framework.upload.UploadHandler;
import com.serli.chell.framework.util.ConvertUtils;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Default form renderer. Render an form without fields.
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FormRenderer implements Serializable {

    public static FormRenderer DEFAULT = new FormRenderer();

    protected FormRenderer() {
    }

    /* -----------------------------------------------------
     *                 Rendering of errors
     * ----------------------------------------------------- */

    public String renderError(Form form, String message) {
        return renderTag("span", message, "error", null, null);
    }

    public String renderErrorSection(Form form) {
        StringBuilder b = new StringBuilder();
        b.append(MessageBundle.getMessage(MessageKey.VALIDATION_FAIL)).append(" :");
        for (String msg : form.getError().values()) {
            b.append(renderErrorSectionItem(form, msg));
        }
        return renderErrorSectionBlock(form, b.toString());
    }

    protected String renderErrorSectionBlock(Form form, String content) {
        return renderTag("ul", content, null, null, null);
    }

    protected String renderErrorSectionItem(Form form, String message) {
        return renderTag("li", message, null, null, null);
    }

    /* -----------------------------------------------------
     *                  Rendering of forms
     * ----------------------------------------------------- */

    public String renderForm(Form form) {
        FormStructure structure = form.getStructure();
        String content = renderFields(form);
        String cssClass = concatCssClasses("chell-form", structure.getCssClass());
        if (structure.hasLegend()) {
            return renderFieldset(structure.getLabel(), content, cssClass, structure.getHtmlId(), null);
        } else {
            return renderTag("div", content, cssClass, structure.getHtmlId(), null);
        }
    }

    public String renderFields(Form form) {
        FormStructure structure = form.getStructure();
        StringBuilder b = new StringBuilder();
        for (FormField field : form.getFields()) {
            if (FormType.HIDDEN.equals(field.getType())) {
                b.append(renderTextInput(form, field));
            } else {
                b.append(renderRow(form, field));
            }
        }
        b.append(renderButtons(form, structure.getButtons()));
        return renderFieldsContainer(form, b.toString());
    }

    protected String renderFieldsContainer(Form form, String fieldsContent) {
        return Constant.EMPTY;
    }

    protected String renderRow(Form form, FormField field) {
        return Constant.EMPTY;
    }

    protected String renderButtonsRow(Form form, String buttonsContainers) {
        return buttonsContainers;
    }

    protected String renderButtonContainer(Form form, String buttonContent) {
        return buttonContent;
    }

    protected String renderButtons(Form form, Collection<FormButton> buttons) {
        StringBuilder b = new StringBuilder();
        for (FormButton button : buttons) {
            b.append(renderButtonContainer(form, renderFormButton(form, button)));
        }
        return renderButtonsRow(form, b.toString());
    }

    public String renderFormButton(Form form, FormButton fb) {
        return renderButton(fb.getHtmlType(), fb.getValue(), null, fb.getOnClick(form), fb.getCssClass(), fb.getHtmlId(), null);
    }

    public String renderField(Form form, FormField field) {
        switch (field.getType()) {
            case TEXT:
            case PASSWORD:
                return renderTextInput(form, field);
            case FILE:
                return renderFileInput(form, field);
            case SELECT:
                return renderSelectInput(form, field);
            case RADIO:
                return renderRadioInput(form, field);
            case CHECKBOX:
                return renderCheckboxInput(form, field);
            case TEXTAREA:
                return renderTextareaInput(form, field);
        }
        return Constant.EMPTY;
    }

    public String renderTextInput(Form form, FormField field) {
        return renderInput(field, field.getValue(form), false);
    }

    public String renderFileInput(Form form, FormField field) {
        String fieldValue = field.getValue(form);
        if (fieldValue == null || fieldValue.length() == 0) {
            return renderInput(field, null, false);
        } else {
            UploadHandler uploadHandler = field.getUploadHandler();
            String itemName = uploadHandler.onDisplay(form.get(field.getName()));
            String deleteButtonLabel = uploadHandler.getDeleteButtonLabel();
            return renderDefinedFileInput(form, field, itemName, deleteButtonLabel);
        }
    }

    public String renderDefinedFileInput(Form form, FormField field, String itemName, String deleteButtonLabel) {
        StringBuilder b = new StringBuilder();
        String onClick = renderUploadDeleteButtonOnClick(form, field);
        String text = renderTag("span", itemName, null, null, null);
        String button = renderButton("button", deleteButtonLabel, null, onClick, null, null, null);
        b.append(renderTag("div", text + button, "input-file-container", null, null));
        return b.toString();
    }

    protected String renderUploadDeleteButtonOnClick(Form form, FormField field) {
        StringBuilder b = new StringBuilder();
        b.append("chell.upload.showInput('input-file-container', '");
        b.append(field.getName()).append("', this);");
        return b.toString();
    }

    public String renderTextareaInput(Form form, FormField field) {
        return renderTextarea(field, field.getValue(form));
    }

    public String renderCheckboxInput(Form form, FormField field) {
        if (field.isMultiValued()) {
            return renderMultipleChoiceInput(form, field, field.getValueSet(form));
        } else {
            boolean checked = ConvertUtils.fromCheckbox(field.getValue(form));
            return renderInput(field, null, checked);
        }
    }

    public String renderRadioInput(Form form, FormField field) {
        String value = field.getValue(form);
        Set<String> values = new HashSet<String>();
        if (value != null) {
            values.add(value);
        }
        return renderMultipleChoiceInput(form, field, values);
    }

    public String renderMultipleChoiceInput(Form form, FormField field, Set<String> values) {
        String content = renderMultipleChoiceInputContent(form, field, values);
        return renderFieldset(null, content, getFieldCssClass(field), getFieldId(field), null);
    }

    protected String renderMultipleChoiceInputContent(Form form, FormField field, Set<String> values) {
        StringBuilder b = new StringBuilder();
        String content, fieldName = field.getName();
        List<DataModel> data = form.getData(fieldName);
        int maxColumns = field.getCols();
        if (maxColumns <= 1) {
            for (DataModel dm : data) {
                content = renderMultipleChoiceInputItem(field, values, dm);
                b.append(renderTag("div", content, null, null, null));
            }
            content = b.toString();
        } else {
            int current = 0;
            StringBuilder s = new StringBuilder();
            for (DataModel dm : data) {
                content = renderMultipleChoiceInputItem(field, values, dm);
                s.append(renderTag("td", content, null, null, null));
                current = (current + 1) % maxColumns;
                if (current == 0) {
                    b.append(renderTag("tr", s.toString(), null, null, null));
                    s.setLength(0);
                }
            }
            if (current != 0) {
                b.append(renderTag("tr", s.toString(), null, null, null));
            }
            content = renderTag("table", b.toString(), null, null, null);
        }
        return content;
    }

    protected String renderMultipleChoiceInputItem(FormField field, Set<String> values, DataModel dm) {
        StringBuilder s = new StringBuilder();
        String value = dm.getValue();
        s.append(renderInput(field, value, values.contains(value), null));
        s.append(Constant.HTML_SPACE).append(renderLabel(field.getName(), dm.getLabel(), null, null, null));
        return s.toString();
    }

    public String renderSelectInput(Form form, FormField field) {
        List<DataModel> data = form.getData(field.getName());
        String options = renderOptions(form, field, data);
        int optionsSize = (field.isMultiValued() ? data.size() : Constant.UNSPECIFIED);
        return renderSelectInput(field, options, optionsSize);
    }

    public String renderSelectInput(FormField field, String options, int optionsSize) {
        return renderSelectInput(field.getName(), field.isMultiValued(), field.getMaxLength(), options, optionsSize, getFieldCssClass(field), getFieldId(field), null);
    }

    public String renderInput(FormField field, String value, boolean checked) {
        return renderInput(field, value, checked, null);
    }

    public String renderInput(FormField field, String value, boolean checked, String style) {
        String type = field.getType().name().toLowerCase();
        return renderInput(type, field.getName(), field.getSize(), field.getCols(), value, checked, getFieldCssClass(field), getFieldId(field), style);
    }

    public String renderTextarea(FormField field, String value) {
        return renderTextarea(field, value, null);
    }

    public String renderTextarea(FormField field, String value, String style) {
        return renderTextarea(field.getName(), field.getCols(), field.getRows(), value, getFieldCssClass(field), getFieldId(field), style);
    }

    public String renderOptions(Form form, FormField formField, List<DataModel> data) {
        String value;
        StringBuilder b = new StringBuilder();
        Set<String> values = formField.getValueSet(form);
        for (DataModel dm : data) {
            value = dm.getValue();
            b.append(renderOption(value, dm.getLabel(), values.contains(value), null, null, null));
        }
        return b.toString();
    }

    public String renderLabel(Form form, FormField field) {
        FormStructure structure = form.getStructure();
        StringBuilder b = new StringBuilder(field.getLabel());
        if (field.isRequired()) {
            b.append(structure.getRequiredHtml());
        }
        b.append(structure.getSeparatorHtml());
        return renderLabel(field.getName(), b.toString());
    }

    /* -----------------------------------------------------
     *                 Rendering of tags
     * ----------------------------------------------------- */

    public String renderSelectInput(String name, boolean multiple, int size, String options, int optionsSize, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<select");
        renderClassStyleId(b, cssClass, id, style);
        b.append(" name=\"").append(name).append('"');
        if (multiple) {
            b.append(" multiple=\"multiple\"");
        }
        if (size > 0) {
            b.append(" size=\"").append(size).append('"');
        } else if (size == Constant.AUTO && optionsSize > 0) {
            b.append(" size=\"").append(optionsSize).append('"');
        }
        b.append('>');
        if (options != null) {
            b.append(options);
        }
        b.append("</select>");
        return b.toString();
    }

    public String renderInput(String type, String name, int size, int maxLength, String value, boolean checked, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<input");
        renderClassStyleId(b, cssClass, id, style, "portlet-form-input-field");
        if (type != null) {
            b.append(" type=\"").append(type).append('"');
        }
        if (name != null) {
            b.append(" name=\"").append(name).append('"');
        }
        if (value != null) {
            b.append(" value=\"").append(value).append('"');
        }
        if (size > 0) {
            b.append(" size=\"").append(size).append('"');
        }
        if ((type.equalsIgnoreCase("text") || type.equalsIgnoreCase("password")) && maxLength > 0) {
            b.append(" maxlength=\"").append(maxLength).append('"');
        }
        if (checked) {
            b.append(" checked=\"checked\"");
        }
        b.append(" />");
        return b.toString();
    }

    public String renderTextarea(String name, int cols, int rows, String value, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<textarea");
        renderClassStyleId(b, cssClass, id, style, "portlet-form-input-field");
        b.append(" name=\"").append(name).append('"');
        if (cols > 0) {
            b.append(" cols=\"").append(cols).append('"');
        }
        if (rows > 0) {
            b.append(" rows=\"").append(rows).append('"');
        }
        b.append(">");
        if (value != null) {
            b.append(value);
        }
        b.append("</textarea>");
        return b.toString();
    }

    public String renderButton(String type, String value, String name, String onClick, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<input");
        renderClassStyleId(b, cssClass, id, style, "portlet-form-button");
        if (type != null) {
            b.append(" type=\"").append(type).append('"');
        }
        if (name != null) {
            b.append(" name=\"").append(name).append('"');
        }
        if (value != null) {
            b.append(" value=\"").append(value).append('"');
        }
        if (onClick != null && onClick.length() > 0) {
            b.append(" onclick=\"").append(onClick).append('"');
        }
        b.append(" />");
        return b.toString();
    }

    public String renderOption(String value, String label, boolean selected, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<option");
        renderClassStyleId(b, cssClass, id, style);
        if (value != null) {
            b.append(" value=\"").append(value).append('"');
        }
        if (selected) {
            b.append(" selected=\"selected\"");
        }
        b.append('>').append(label).append("</option>");
        return b.toString();
    }

    public String renderLabel(String forField, String label) {
        return renderLabel(forField, label, null, null, null);
    }

    public String renderLabel(String forField, String label, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<label for=\"").append(forField).append('"');
        renderClassStyleId(b, cssClass, id, style);
        b.append('>');
        b.append(label).append("</label>");
        return b.toString();
    }

    public String renderFieldset(String legend, String content, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<fieldset");
        renderClassStyleId(b, cssClass, id, style);
        b.append('>');
        if (legend != null && legend.length() > 0) {
            b.append("<legend>").append(legend).append("</legend>");
        }
        b.append(content).append("</fieldset>");
        return b.toString();
    }

    public String renderTable(String content, String cellSpacing, String cellPadding, String border, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<table");
        renderClassStyleId(b, cssClass, id, style);
        if (cellSpacing != null && cellSpacing.length() > 0) {
            b.append(" cellspacing=\"").append(cellSpacing).append('"');
        }
        if (cellPadding != null && cellPadding.length() > 0) {
            b.append(" cellpadding=\"").append(cellPadding).append('"');
        }
        if (border != null && border.length() > 0) {
            b.append(" border=\"").append(border).append('"');
        }
        b.append('>').append(content).append("</table>");
        return b.toString();
    }

    public String renderForm(String content, String action, String method, String accept, String enctype,
                             String acceptCharset, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append("<form");
        renderClassStyleId(b, cssClass, id, style);
        if (action != null && action.length() > 0) {
            b.append(" action=\"").append(action).append('"');
        }
        if (method != null && method.length() > 0) {
            b.append(" method=\"").append(method).append('"');
        }
        if (accept != null && accept.length() > 0) {
            b.append(" accept=\"").append(accept).append('"');
        }
        if (enctype != null && enctype.length() > 0) {
            b.append(" enctype=\"").append(enctype).append('"');
        }
        if (acceptCharset != null && acceptCharset.length() > 0) {
            b.append(" accept-charset=\"").append(acceptCharset).append('"');
        }
        b.append('>').append(content).append("</form>");
        return b.toString();
    }

    public String renderTag(String tag, String content, String cssClass, String id, String style) {
        StringBuilder b = new StringBuilder();
        b.append('<').append(tag);
        renderClassStyleId(b, cssClass, id, style);
        b.append('>').append(content);
        b.append("</").append(tag).append('>');
        return b.toString();
    }

    protected void renderClassStyleId(StringBuilder b, String cssClass, String id, String style, String defaultCssClass) {
        String allCssClasses = getCssClasses(defaultCssClass, cssClass);
        if (allCssClasses != null) {
            b.append(" class=\"").append(allCssClasses).append('"');
        }
        if (style != null && style.length() > 0) {
            b.append(" style=\"").append(style).append('"');
        }
        if (id != null && id.length() > 0) {
            b.append(" id=\"").append(getId(id)).append('"');
        }
    }

    protected void renderClassStyleId(StringBuilder b, String cssClass, String id, String style) {
        renderClassStyleId(b, cssClass, id, style, null);
    }

    protected String concatCssClasses(String source, String cssClass) {
        if (source == null || source.length() == 0) {
            return cssClass;
        } else if (cssClass == null || cssClass.length() == 0) {
            return source;
        } else {
            StringBuilder b = new StringBuilder();
            b.append(source).append(' ');
            b.append(cssClass);
            return b.toString();
        }
    }

    protected String getCssClasses(String... cssClasses) {
        StringBuilder b = new StringBuilder();
        for (String cssClass : cssClasses) {
            if (cssClass != null && cssClass.length() > 0) {
                b.append(' ').append(cssClass);
            }
        }
        if (b.length() > 0) {
            return b.substring(1);
        } else {
            return null;
        }
    }

    protected String getFieldCssClass(FormField field) {
        return field.getCssClass();
    }

    protected String getFieldId(FormField field) {
        return field.getHtmlId();
    }

    protected String getId(String id) {
        if (id.startsWith(Constant.NAMESPACE_ID_PREFIX)) {
            return PortletHelper.getNamespace() + id.substring(Constant.NAMESPACE_ID_PREFIX.length());
        }
        return id;
    }
}
