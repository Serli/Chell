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
