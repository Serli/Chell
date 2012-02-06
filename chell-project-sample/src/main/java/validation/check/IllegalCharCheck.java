package validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.validation.Check;
import com.serli.chell.framework.validation.annotation.CheckWith;
import java.util.regex.Pattern;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class IllegalCharCheck extends Check<String> {

    private Pattern pattern = Pattern.compile("[a-z0-9_-]*", Pattern.CASE_INSENSITIVE);

    public IllegalCharCheck(CheckWith annotation) {
        super(annotation);
    }
    
    public boolean validate(Form form, String fieldName, String fieldValue) {
        return pattern.matcher(fieldValue).matches();
    }
}
