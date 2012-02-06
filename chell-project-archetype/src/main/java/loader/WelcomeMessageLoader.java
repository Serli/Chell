
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
