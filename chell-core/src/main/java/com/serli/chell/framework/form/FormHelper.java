
package com.serli.chell.framework.form;

import com.serli.chell.framework.exception.ChellException;
import com.serli.chell.framework.PortletHelper;
import com.serli.chell.framework.Scope;
import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.PhaseType;
import com.serli.chell.framework.store.Store;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class FormHelper {

    protected FormHelper() {
    }

    public static <T extends Form> T get(Class<T> formClass) {
        T form;
        Store requestStore = Scope.request();
        if (PhaseType.ACTION.equals(PortletHelper.getPhase()) &&
            !PortletHelper.getActionName().equals(Constant.ACTION_CANCEL)) {
            form = createFromRequest(formClass);
            storeForm(requestStore, form);
            return form;
        } else {
            form = getStoredForm(requestStore, formClass);
            if (form == null) {
                Store sessionStore = Scope.session();
                form = getStoredForm(sessionStore, formClass);
                if (form == null) {
                    form = createFromPreferences(formClass);
                    storeForm(requestStore, form);
                }
            }
            return form;
        }
    }

    public static <T extends Form> T create(Class<T> formClass) {
        try {
            return formClass.newInstance();
        } catch (Exception ex) {
            throw new ChellException(ex);
        }
    }

    public static <T extends Form> T createFromPreferences(Class<T> formClass) {
        T form = create(formClass);
        form.fillFromPreferences();
        return form;
    }

    public static <T extends Form> T createFromRequest(Class<T> formClass) {
        T form = create(formClass);
        form.fillFromRequest();
        return form;
    }

    private static <T extends Form> String getFormStoreKey(Class<T> formClass) {
        StringBuilder b = new StringBuilder();
        b.append(Constant.KEY_FORMS);
        b.append(formClass.getName());
        return b.toString();
    }

    public static <T extends Form> T getStoredForm(Store store, Class<T> formClass) {
        return (T) store.get(getFormStoreKey(formClass));
    }

    public static void storeForm(Store store, Form form) {
        store.set(getFormStoreKey(form.getClass()), form);
    }

    public static Form removeStoredForm(Store store, Form form) {
        return (Form) store.remove(getFormStoreKey(form.getClass()));
    }

    public static <T extends Form> T removeStoredForm(Store store, Class<? extends Form> formClass) {
        return (T) store.remove(getFormStoreKey(formClass));
    }
}
