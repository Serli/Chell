package controller;

import form.EditForm;
import com.serli.chell.framework.Render;
import com.serli.chell.framework.annotation.OnAction;
import com.serli.chell.framework.annotation.OnRender;
import com.serli.chell.framework.constant.RenderMode;

public class ProjectArchetype {

    /**
     * Called on render view phase.
     */
    @OnRender(RenderMode.VIEW)
    public void render(EditForm ef) {
        Render.attr("prefix", ef.getCurrentValueLabel(EditForm.WELCOME_MESSAGE));
        Render.attr("name", ef.getName());
    }

    /* -------------------------------------------------------
     *   Mode EDIT
     * ------------------------------------------------------- */

    /**
     * Called on render edit phase.
     */
    @OnRender(RenderMode.EDIT)
    public void renderEdit(EditForm ef) {
        Render.form(ef);
    }

    /**
     * Called on action named savePreferences.
     */
    @OnAction("savePreferences")
    public void savePreferences(EditForm ef) {
        if (ef.validate()) {
            ef.saveInPreferences();
        }
    }
}