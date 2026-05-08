import com.flooringmastery.controller.FlooringMasterController;
import com.flooringmastery.dao.*;
import com.flooringmastery.service.FlooringMasterServiceLayer;
import com.flooringmastery.service.FlooringMasterServiceLayerImpl;
import com.flooringmastery.ui.FlooringMasterView;
import com.flooringmastery.ui.UserIO;
import com.flooringmastery.ui.UserIOConsoleImpl;

public class App {
    public static void main(String[] args) {

        UserIO io = new UserIOConsoleImpl();
        FlooringMasterView view = new FlooringMasterView(io);

        // Instantiate the DAO
        // OrderDao orderDao = new OrderDaoImpl();
        // TaxDao taxDao = new TaxDaoImpl();
        // ProductDao productDao = new ProductDaoImpl();

        // Instantiate the Service Layer
        // TODO: connect the DAOs to it
        FlooringMasterServiceLayer serviceLayer = new FlooringMasterServiceLayerImpl();

        // Instantiate the Controller and wire the Service Layer into it
        FlooringMasterController controller = new FlooringMasterController(view, serviceLayer);

        // Kick off the Controller
        controller.run();
    }
}
