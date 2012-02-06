
package com.serli.chell.framework.loader;

import com.serli.chell.framework.constant.Constant;
import java.io.Serializable;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public interface DataModel extends Serializable {
    
    String getValue();
    String getLabel();

    public static DataModel EMPTY = new DataModel() {
        public String getValue() {
            return Constant.EMPTY;
        }

        public String getLabel() {
            return Constant.EMPTY;
        }
    };
}
