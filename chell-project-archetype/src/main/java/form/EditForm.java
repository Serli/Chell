package form;

import com.serli.chell.framework.constant.CancelAction;
import com.serli.chell.framework.form.annotation.HtmlForm;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.annotation.HtmlButtonCancel;
import com.serli.chell.framework.form.annotation.HtmlInputSelect;
import com.serli.chell.framework.form.annotation.HtmlInputText;
import com.serli.chell.framework.validation.annotation.Length;
import com.serli.chell.framework.validation.annotation.Required;
import loader.WelcomeMessageLoader;


@HtmlForm(key = "message.edit.preferences")
@HtmlButtonCancel(action = CancelAction.SWITCH_TO_VIEW_MODE)
public class EditForm extends Form {

    public static final String WELCOME_MESSAGE = "welcomeMessage";
    public static final String NAME = "name";

    @Required
    @HtmlInputSelect(key = "field.welcome.message", loader = WelcomeMessageLoader.class)
    private String welcomeMessage = WelcomeMessageLoader.HELLO;

    @Length(min = 2, max = 8)
    @HtmlInputText(key = "field.name", size = 20)
    private String name = "World";

    public EditForm() {
        super("ef");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public void setWelcomeMessage(String welcomeMessage) {
        this.welcomeMessage = welcomeMessage;
    }
}
