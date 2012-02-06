
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.validation.annotation.Email;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class EmailCheck extends RegexCheck {

    private static final String EMAIL_REGEX = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[a-zA-Z0-9](?:[\\w-]*[\\w])?";

    public EmailCheck(Email annotation) {
        super(annotation, EMAIL_REGEX);
    }
}
