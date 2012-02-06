
package loader;

import com.serli.chell.framework.Bootstrap;
import com.serli.chell.framework.message.MessageBundle;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class ApplicationBootstrap extends Bootstrap {
    protected void onBootstrap() {
        MessageBundle.loadBundle("validation");
    }
}
