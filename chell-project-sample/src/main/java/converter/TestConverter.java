
package converter;

import com.serli.chell.framework.form.converter.FieldConverter;
import com.serli.chell.framework.util.ConvertUtils;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class TestConverter implements FieldConverter<Integer[], String[]> {

    public String[] fromObject(Integer[] objectValue) {
        return ConvertUtils.fromIntegers(objectValue);
    }

    public Integer[] toObject(String[] fieldValue) {
        return ConvertUtils.toIntegers(fieldValue);
    }
}
