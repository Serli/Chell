
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.validation.annotation.Url;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class UrlCheck extends RegexCheck {

    private static final String URL_REGEX = "^(http|https|ftp)\\://[a-zA-Z0-9\\-\\.]+\\.[a-zA-Z]{2,3}(:[a-zA-Z0-9]*)?/?([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~])*$";

    public UrlCheck(Url annotation) {
        super(annotation, URL_REGEX);
    }
}
