
package com.serli.chell.framework.validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.validation.Check;
import java.lang.annotation.Annotation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public abstract class RegexCheck extends Check<String> {

    private Pattern pattern;

    public RegexCheck(Annotation annotation, String regex) {
        super(annotation);
        this.pattern = Pattern.compile(regex);
    }

    public boolean validate(Form form, String fieldName, String fieldValue) {
        Matcher matcher = pattern.matcher(fieldValue);
        return matcher.matches();
    }
}
