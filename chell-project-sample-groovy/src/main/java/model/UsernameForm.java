package model;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.annotation.HtmlInputText;


/**
 * Representation of the username submission form.
 */
public class UsernameForm extends Form {

    @HtmlInputText(key = "field.username")
    private String username;

    public UsernameForm() {
        super("uf");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
