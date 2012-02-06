
package loader;

import com.serli.chell.framework.loader.DataLoader;
import com.serli.chell.framework.loader.DataModel;
import com.serli.chell.framework.loader.DataModelKey;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class AnimalLoader implements DataLoader {

    public AnimalLoader() {
    }

    public List<DataModel> getData() {
        System.out.println("Load Data...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
        List<DataModel> elements = new ArrayList<DataModel>();
        elements.add(new DataModelKey("dog", "select.animals.dog"));
        elements.add(new DataModelKey("cat", "select.animals.cat"));
        elements.add(new DataModelKey("bird", "select.animals.bird"));
        elements.add(new DataModelKey("rabbit", "select.animals.rabbit"));
        elements.add(new DataModelKey("snake", "select.animals.snake"));
        return elements;
    }
}
