package com.serli.chell.framework.form.render;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.FormField;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class TableFormRenderer extends FormRenderer {

    @Override
    protected String renderFieldsContainer(Form form, String fieldsContent) {
        return renderTag("table", fieldsContent, "chell-container", null, null);
    }

    @Override
    protected String renderRow(Form form, FormField field) {
        StringBuilder b = new StringBuilder();
        b.append(renderTag("td", renderLabel(form, field), "portlet-form-label", null, null));
        b.append(renderTag("td", renderField(form, field), "portlet-form-field", null, null));
        return renderTag("tr", b.toString(), "chell-row", null, null);
    }

    @Override
    protected String renderButtonsRow(Form form, String buttonContent) {
        StringBuilder b = new StringBuilder();
        b.append("<td colspan=\"2\">").append(buttonContent).append("</td>");
        return renderTag("tr", b.toString(), "chell-button-row", null, null);
    }
}
