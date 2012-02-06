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

package com.serli.chell.framework.resource;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.portlet.ResourceURL;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
@Target({ ANNOTATION_TYPE, TYPE })
@Retention(RUNTIME)
@Documented
@Inherited
public @interface Resource {

    String path();
    Cacheability cacheability() default Cacheability.FULL;

    @Target(ANNOTATION_TYPE)
    @Retention(RUNTIME)
    @Documented
    @Inherited
    @interface List {
        Resource[] value();
    }

    public enum Cacheability {

        FULL(ResourceURL.FULL),
        PORTLET(ResourceURL.PORTLET),
        PAGE(ResourceURL.PAGE);

        private String cacheability;

        private Cacheability(String cacheability) {
            this.cacheability = cacheability;
        }

        public String getCacheability() {
            return cacheability;
        }
    }
}
