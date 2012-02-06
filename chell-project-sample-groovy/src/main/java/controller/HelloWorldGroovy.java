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

package controller;

import com.serli.chell.framework.util.F.Option;
import com.serli.chell.framework.constant.RenderMode;
import com.serli.chell.framework.PortletHelper.Model;
import com.serli.chell.framework.Render;
import com.serli.chell.framework.annotation.Controller;
import com.serli.chell.framework.annotation.OnAction;
import com.serli.chell.framework.annotation.OnRender;
import com.serli.chell.framework.resolver.HtmlViewResolver;
import model.PrefsForm;
import model.UsernameForm;

@Controller(viewResolver = HtmlViewResolver.class)
public class HelloWorldGroovy {

    /**
     * Called on render view phase.
     */
    @OnRender(RenderMode.VIEW)
    public void render(Model model, PrefsForm prefForm) {

        // get username transformation based on preferences
        UsernameAction action = new UsernameAction(prefForm);

        // get username from model

        Option<String> username = model.forKey("username", String.class);

        if (!username.isDefined()) {
            // if no username in model
            Render.attr("username", action.apply("Unknown"));
        } else {
            // if username in model (after submit action)
            Render.attr("username", action.apply(username.get()));
        }
        // render a new username form
        Render.form(new UsernameForm());
    }

    /**
     * Called on render edit phase.
     */
    @OnRender(RenderMode.EDIT)
    public void renderEdit(PrefsForm prefForm) {
        Render.form(prefForm);
    }

    /**
     * Called on action named submitUsername.
     */
    @OnAction("submitUsername")
    public void submitUsername(UsernameForm form) {
        Render.attr("username", form.getUsername());
    }

    /**
     * Called on action named savePreferences.
     */
    @OnAction("savePreferences")
    public void savePreferences(PrefsForm prefForm) {
        if (prefForm.validate()) {
            prefForm.saveInPreferences();
        }
    }

    /**
     * Transformation class for the username.
     * Returns an uppercased username based on preferences.
     */
    public static class UsernameAction {
        
        private final boolean uppercase;

        public UsernameAction(PrefsForm prefForm) {
            this.uppercase = prefForm.getUpper().equals("on");
        }

        public String apply(String value) {
            if (uppercase) {
                return value.toUpperCase();
            } else {
                return value;
            }
        }
    }
}