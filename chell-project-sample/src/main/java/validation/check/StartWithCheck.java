package validation.check;

import com.serli.chell.framework.form.Form;
import com.serli.chell.framework.validation.Check;
import java.text.MessageFormat;
import validation.annotation.StartWith;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class StartWithCheck extends Check<String> {

    private String startWith;

    public StartWithCheck(StartWith annotation) {
        super(annotation);
        startWith = annotation.value();
    }
    
    public boolean validate(Form form, String fieldName, String fieldValue) {
        return fieldValue.startsWith(startWith);
    }

    @Override
    protected String formatErrorMessage(String pattern, String fieldLabel) {
        return MessageFormat.format(pattern, fieldLabel, startWith);
    }
}
