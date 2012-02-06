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


package loader;

import com.serli.chell.framework.loader.DataLoader;
import com.serli.chell.framework.loader.DataModel;
import com.serli.chell.framework.loader.DataModelKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class WelcomeMessageLoader implements DataLoader {

    public static final String HELLO = "hello";
    public static final String WELCOME = "welcome";
    public static final String HI = "hi";

    List<DataModel> welcomeMessages;

    public WelcomeMessageLoader() {
        welcomeMessages = new ArrayList<DataModel>();
        welcomeMessages.add(DataModel.EMPTY);
        welcomeMessages.add(new DataModelKey(HELLO, "select.welcome.message.hello"));
        welcomeMessages.add(new DataModelKey(WELCOME, "select.welcome.message.welcome"));
        welcomeMessages.add(new DataModelKey(HI, "select.welcome.message.hi"));
    }

    public List<DataModel> getData() {
        return welcomeMessages;
    }
}
