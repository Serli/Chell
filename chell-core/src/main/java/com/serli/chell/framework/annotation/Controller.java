package com.serli.chell.framework.annotation;

import com.serli.chell.framework.constant.Constant;
import com.serli.chell.framework.resolver.JspViewResolver;
import com.serli.chell.framework.resolver.ViewResolver;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
// TODO : Gestion avec annnotation generique !
@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Inherited
public @interface Controller {
    String baseViewPath() default Constant.RESOLVER_BASE_VIEW_PATH;
    String viewFolderName() default Constant.EMPTY;
    Class<? extends ViewResolver> viewResolver() default JspViewResolver.class;
    Catcher[] exceptionCatchers() default {};
}
