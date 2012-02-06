package com.serli.chell.framework.form;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class FormStructureRegistry {

    private static Map<Class<? extends Form>, FormStructure> formStructuresByClass = new HashMap<Class<? extends Form>, FormStructure>();

    public static synchronized FormStructure getFormStructure(Class<? extends Form> formClass) {
        FormStructure structure = formStructuresByClass.get(formClass);
        if (structure == null) {
            structure = new FormStructure(formClass);
            formStructuresByClass.put(formClass, structure);
        }
        return structure;
    }
}
