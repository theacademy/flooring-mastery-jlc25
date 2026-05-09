import com.flooringmastery.controller.FlooringMasterController;
import com.flooringmastery.dao.*;
import com.flooringmastery.service.FlooringMasterServiceLayer;
import com.flooringmastery.service.FlooringMasterServiceLayerImpl;
import com.flooringmastery.ui.FlooringMasterView;
import com.flooringmastery.ui.UserIO;
import com.flooringmastery.ui.UserIOConsoleImpl;

import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) throws FileNotFoundException {

        UserIO io = new UserIOConsoleImpl();
        FlooringMasterView view = new FlooringMasterView(io);

        // Instantiate the DAO
        OrderDao orderDao = new OrderDaoImpl("FlooringMastery/SampleFileData/Orders");
        TaxDao taxDao = new TaxDaoImpl("FlooringMastery/SampleFileData/Data/Taxes.txt");
        ProductDao productDao = new ProductDaoImpl("FlooringMastery/SampleFileData/Data/Products.txt");

        // Instantiate the Service Layer
        // TODO: connect the DAOs to it
        FlooringMasterServiceLayer serviceLayer = new FlooringMasterServiceLayerImpl(orderDao, taxDao, productDao);

        // Instantiate the Controller and wire the Service Layer into it
        FlooringMasterController controller = new FlooringMasterController(view, serviceLayer);

        // Kick off the Controller
        controller.run();
    }
}
