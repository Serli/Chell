
package loader;

import com.serli.chell.framework.loader.DataLoader;
import com.serli.chell.framework.loader.DataModel;
import com.serli.chell.framework.loader.DataModelMessage;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class CityLoader implements DataLoader {

    private List<DataModel> cities;

    public CityLoader() {
        cities = new ArrayList<DataModel>(5);
        cities.add(new DataModelMessage("79000", "Niort"));
        cities.add(new DataModelMessage("86000", "Poitiers"));
        cities.add(new DataModelMessage("33000", "Bordeaux"));
        cities.add(new DataModelMessage("75000", "Paris"));
        cities.add(new DataModelMessage("13000", "Marseille"));
    }

    public List<DataModel> getData() {
        return cities;
    }
}
