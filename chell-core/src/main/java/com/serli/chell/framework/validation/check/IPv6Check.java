
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.validation.annotation.IPv6;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class IPv6Check extends RegexCheck {

    private static final String IPV6_REGEX = "[0-9a-f]{1,4}(:[0-9a-f]{1,4}){7}";

    public IPv6Check(IPv6 annotation) {
        super(annotation, IPV6_REGEX);
    }
}
