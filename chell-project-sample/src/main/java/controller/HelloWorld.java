package controller;

import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.constant.RenderMode;
import com.serli.chell.framework.PortletHelper.Model;
import com.serli.chell.framework.Render;
import com.serli.chell.framework.annotation.Catcher;
import com.serli.chell.framework.annotation.Controller;
import com.serli.chell.framework.annotation.OnAction;
import com.serli.chell.framework.annotation.OnRender;
import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.message.MessageBundle;
import exception.MagicExceptionHandler;
import exception.SampleException;
import exception.SampleExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.portlet.PortletException;
import model.PrefsForm;
import model.UsernameForm;

@Controller(exceptionCatchers = {
    @Catcher(exception = SampleException.class, handler = SampleExceptionHandler.class),
    @Catcher(exception = PortletException.class, handler = MagicExceptionHandler.class)
})
public class HelloWorld {

    /* -------------------------------------------------------
     *   Mode VIEW
     * ------------------------------------------------------- */

    /**
     * Called on render view phase.
     */
    @OnRender(RenderMode.VIEW)
    public void render(Model model, PrefsForm prefForm) {
        // Read preference form and model
        String username = (String) model.get("username");
        String defaultUsername = prefForm.getDefaultName();
        String welcomePrefix = prefForm.getCurrentValueLabel(PrefsForm.WELCOME_PREFIX);
        String[] animals =  prefForm.getCurrentValueLabels(PrefsForm.ANIMALS);
        Date birthday = prefForm.getAsDate(PrefsForm.BIRTHDAY);
        boolean upper = prefForm.getAsBool(PrefsForm.UPPER);

        // Create messages
        String welcomeMsg = getWelcomeMessage(welcomePrefix, username, defaultUsername, upper);
        String animalsMsg = getAnimalsMessage(animals);
        String birthdayMsg = getBirthdayMessage(birthday);


        System.out.println("Age : " + prefForm.getAsInteger(PrefsForm.AGE));
        Integer[] cities = prefForm.getAsIntegers(PrefsForm.CITIES);
        if (cities != null) {
            System.out.println("Listing cities...");
            for (Integer i : cities) {
                System.out.println("City : " + i);
            }
        } else {
            System.out.println("No cities");
        }
        System.out.println("LOGO : " + prefForm.getAsFile(PrefsForm.LOGO));

        // Add messages to the view
        Render.attr("welcomeMsg", welcomeMsg);
        Render.attr("animalsMsg", animalsMsg);
        Render.attr("birthdayMsg", birthdayMsg);
        Render.form(new UsernameForm());
    }

    /**
     * Called on action named submitUsername.
     */
    @OnAction("submitUsername")
    public void submitUsername(UsernameForm form) throws PortletException {
        try {
            String username = form.getUsername();
            if (username == null || username.length() == 0) {
                throw new SampleException("Impossible de dire bonjour a personne !");
            } else if (username.equals("magic")) {
                throw new PortletException("Ceci est une commande spéciale magique !!!");
            } else if (username.equals("error")) {
                throw new ChellException("Pas bien !!!");
            }
            Render.attr("username", username);
            PortletHelper.setTitle(username);
        } catch (SampleException ex) {
            throw new PortletException(ex);
        }
    }

    private String getWelcomeMessage(String welcomePrefix, String username, String defaultUsername, boolean upper) {
        StringBuilder b = new StringBuilder();
        b.append(welcomePrefix).append(' ');
        if (username == null || username.length() == 0) {
            username = defaultUsername;
        }
        if (upper) {
            b.append(username.toUpperCase());
        } else {
            b.append(username);
        }
        b.append(" !!!!");
        return b.toString();
    }

    private String getAnimalsMessage(String[] animals) {
        if (animals.length > 0) {
            StringBuilder b = new StringBuilder();
            for (String animal : animals) {
                b.append(animal).append(", ");
            }
            b.setLength(b.length() - 2);
            String animalList = b.toString();
            return MessageBundle.getMessageFormatted("message.animals.list", animalList);
        } else {
            return MessageBundle.getMessage("message.animals.not");
        }
    }

    private String getBirthdayMessage(Date birthday) {
        if (birthday == null) {
            String notSpecified = MessageBundle.getMessage("message.not.specified");
            return MessageBundle.getMessageFormatted("message.birthday", notSpecified);
        } else {
            DateFormat format = new SimpleDateFormat("dd MMMM yyyy");
            return MessageBundle.getMessageFormatted("message.birthday", format.format(birthday));
        }
    }

    /* -------------------------------------------------------
     *   Mode EDIT
     * ------------------------------------------------------- */

    /**
     * Called on render edit phase.
     */
    @OnRender(RenderMode.EDIT)
    public String renderEdit(PrefsForm prefForm) {
        Render.form(prefForm);

        //return "edit-custom";
        //return "edit-custom-taglib";
        return "edit-taglib";
        //return null;
    }

    /**
     * Called on action named savePreferences.
     */
    @OnAction("savePreferences")
    public void savePreferences(PrefsForm form) {
        if (form.validate()) {
            form.saveInPreferences();
        }
    }
}