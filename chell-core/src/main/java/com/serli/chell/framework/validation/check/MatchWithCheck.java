
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.validation.annotation.MatchWith;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class MatchWithCheck extends RegexCheck {

    public MatchWithCheck(MatchWith annotation) {
        super(annotation, annotation.value());
    }
}
