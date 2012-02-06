package model;

import com.serli.chell.framework.form.annotation.HtmlButtonCancel;
import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.form.annotation.HtmlForm;
import com.serli.chell.framework.constant.CancelAction;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.form.annotation.HtmlInputCheckbox;
import com.serli.chell.framework.form.annotation.HtmlInputFile;
import com.serli.chell.framework.form.annotation.HtmlInputSelect;
import com.serli.chell.framework.form.annotation.HtmlInputText;
import com.serli.chell.framework.form.annotation.HtmlInputTextArea;
import com.serli.chell.framework.form.render.TableFormRenderer;
import com.serli.chell.framework.resource.Resource;
import com.serli.chell.framework.validation.annotation.CheckWith;
import com.serli.chell.framework.validation.annotation.IntegerNumber;
import com.serli.chell.framework.validation.annotation.Length;
import com.serli.chell.framework.validation.annotation.Size;
import com.serli.chell.framework.validation.annotation.Required;
import com.serli.chell.framework.validation.annotation.Temporal;
import handler.AllUploadHandler;
import handler.ImageUploadHandler;
import loader.AnimalLoader;
import loader.CityLoader;
import loader.WelcomeMessageLoader;
import ui.HtmlButtonHello;
import validation.annotation.StartWith;
import validation.check.IllegalCharCheck;

/**
 * Representation of the preference form.
 * This form validates the input values.
 */
@Resource(path = "@/style/form.css")
@HtmlForm(key = "message.edit.preferences", renderer = TableFormRenderer.class)
@HtmlButtonCancel(action = CancelAction.DO_NOTHING)
@HtmlButtonHello
public class PrefsForm extends Form {

    // Names of form fields
    public static final String WELCOME_PREFIX = "welcomePrefix";
    public static final String DEFAULT_NAME = "defaultName";
    public static final String UPPER = "upper";
    public static final String ANIMALS = "animals";
    public static final String COMMENT = "comment";
    public static final String CITIES = "cities";
    public static final String AGE = "age";
    public static final String LOGO = "logo";
    public static final String BIRTHDAY = "birthday";

    @Required
    @HtmlInputSelect(key = "field.welcome.message", loader = WelcomeMessageLoader.class)
    private String welcomePrefix = WelcomeMessageLoader.HELLO;

    @Required
    @Length(max = 8)
    @StartWith(value = "V")
    @CheckWith(value = IllegalCharCheck.class, key = "validation.illegal.char")
    @HtmlInputText(key = "field.default.name", size = 20)
    private String defaultName = "Vincent";

    @HtmlInputCheckbox(key = "field.upper.mode")
    private String upper = ON;

    @Size(min = 2, max = 4)
    @HtmlInputSelect(key = "field.animals", loader = AnimalLoader.class, rows = 6)
    private String[] animals;

    @Required
    @IntegerNumber(min = 1)
    @HtmlInputText(key = "field.age", size = 2, maxLength = 3)
    private String age;

    @HtmlInputFile(key = "field.logo", handler = ImageUploadHandler.class)
    private String logo;

    @Required
    @IntegerNumber
    @HtmlInputCheckbox(key = "field.cities", loader = CityLoader.class, cols = 5)
    private String[] cities;

    @HtmlInputTextArea(key = "field.comment", cols = 30, rows = 5)
    private String comment;

    @Temporal(pattern = "dd/MM/yyyy", after = "1/1/1900", before = Constant.TODAY)
    @HtmlInputText(key = "field.birthday")
    private String birthday;

    @HtmlInputFile(key = "field.attachement", handler = AllUploadHandler.class)
    private String attachement;

    public PrefsForm() {
        super("pf");
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public String getWelcomePrefix() {
        return welcomePrefix;
    }

    public void setWelcomePrefix(String welcomePrefix) {
        this.welcomePrefix = welcomePrefix;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public String[] getAnimals() {
        return animals;
    }

    public void setAnimals(String[] animals) {
        this.animals = animals;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String[] getCities() {
        return cities;
    }

    public void setCities(String[] cities) {
        this.cities = cities;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAttachement() {
        return attachement;
    }

    public void setAttachement(String attachement) {
        this.attachement = attachement;
    }
}