
package com.serli.chell.framework.form.render.el;

import com.serli.chell.framework.form.Form;
import java.util.HashMap;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class AbstractFieldAccessor<T> extends HashMap<String, T> {

    protected Form form;

    public AbstractFieldAccessor(Form form) {
        super();
        this.form = form;
    }

    public AbstractFieldAccessor(Form form, int initialCapacity) {
        super(initialCapacity);
        this.form = form;
    }
}
