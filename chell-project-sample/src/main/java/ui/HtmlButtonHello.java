/*
 *  Copyright 2011-2012 SERLI (www.serli.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package ui;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.constant.FormType;
import com.serli.chell.framework.form.FormButton.ButtonType;
import com.serli.chell.framework.reflect.AnnotationMetaData;
import com.serli.chell.framework.reflect.AnnotationMetaData.Type;
import com.serli.chell.framework.reflect.AnnotationConstants;
import com.serli.chell.framework.reflect.AnnotationDefaultValues;
import com.serli.chell.framework.reflect.AnnotationDefaultValues.DefaultValue;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import ui.HtmlButtonHello.HelloButtonConstants;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
@AnnotationConstants(HelloButtonConstants.class)
public @interface HtmlButtonHello {

    @AnnotationMetaData(Type.MESSAGE_KEY)
    String key() default "action.hello";

    @AnnotationMetaData(Type.MESSAGE_TEXT)
    String label() default Constant.EMPTY;

    @AnnotationMetaData(Type.MESSAGE_BUNDLE)
    String bundle() default Constant.EMPTY;

    @AnnotationMetaData(Type.HTML_CSS_CLASS)
    String cssClass() default Constant.EMPTY;

    @AnnotationMetaData(Type.HTML_ID)
    String id() default Constant.EMPTY;

    @AnnotationMetaData(Type.BUTTON_ORDER)
    int order() default Constant.DEFAULT_ORDER;

    @AnnotationMetaData(Type.BUTTON_NAME)
    String name() default "custom";

    public static class HelloButtonConstants implements AnnotationDefaultValues {
        public DefaultValue[] getDefaultValues() {
            return new DefaultValue[] {
                new DefaultValue(Type.FORM_TYPE, FormType.BUTTON),
                new DefaultValue(Type.BUTTON_TYPE, ButtonType.BUTTON),
                new DefaultValue(Type.BUTTON_ON_CLICK, "window.alert('Hello')")
            };
        }
    }
}
