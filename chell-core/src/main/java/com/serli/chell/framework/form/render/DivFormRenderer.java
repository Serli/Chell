package com.serli.chell.framework.form.render;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;
import java.util.Set;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DivFormRenderer extends FormRenderer {

    public static final String INLINE_DISPLAY_STYLE = "display: inline;";

    @Override
    protected String renderFieldsContainer(Form form, String fieldsContent) {
        return renderTag("div", fieldsContent, "chell-container", null, null);
    }

    @Override
    protected String renderRow(Form form, FormField field) {
        StringBuilder b = new StringBuilder();
        b.append(renderTag("span", renderLabel(form, field), "portlet-form-label", null, null));
        b.append(renderTag("span", renderField(form, field), "portlet-form-field", null, null));
        return renderTag("div", b.toString(), "chell-row", null, null);
    }

    @Override
    protected String renderButtonsRow(Form form, String buttonsContainers) {
        return renderTag("div", buttonsContainers, "chell-button-row", null, null);
    }

    @Override
    public String renderMultipleChoiceInput(Form form, FormField field, Set<String> values) {
        String content = renderMultipleChoiceInputContent(form, field, values);
        return renderFieldset(null, content, getFieldCssClass(field), getFieldId(field), INLINE_DISPLAY_STYLE);
    }
}
