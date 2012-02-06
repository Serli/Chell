package model;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.annotation.HtmlInputText;
import com.serli.chell.framework.validation.annotation.Length;
import com.serli.chell.framework.validation.annotation.Required;
import com.serli.chell.framework.validation.annotation.SetOf;

/**
 * Representation of the preference form.
 * This form validates the input values.
 */
public class PrefsForm extends Form {

    @Required
    @Length(min = 2, max = 3)
    @SetOf({ "on", "off"})
    @HtmlInputText(key = "field.upper.mode")
    private String upper = "off";

    public PrefsForm() {
        super("pf");
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }
}