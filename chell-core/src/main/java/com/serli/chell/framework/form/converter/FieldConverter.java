
package com.serli.chell.framework.form.converter;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface FieldConverter<ObjectType, FieldType> {

    /**
     * @param objectValue Never null.
     */
    FieldType fromObject(ObjectType objectValue);

    /**
     * @param fieldValue Never null.
     */
    ObjectType toObject(FieldType fieldValue);
}
