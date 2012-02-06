
package com.serli.chell.framework.form.converter;

import com.serli.chell.framework.exception.ChellException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class DateFieldConverter implements FieldConverter<Date, String> {

    private DateFormat format;

    public DateFieldConverter(DateFormat format) {
        this.format = format;
    }

    public String fromObject(Date objectValue) {
        return format.format(objectValue);
    }

    public Date toObject(String fieldValue) {
        try {
            return format.parse(fieldValue);
        } catch (ParseException ex) {
            throw new ChellException(ex);
        }
    }
}
