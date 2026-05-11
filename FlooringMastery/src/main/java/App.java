import com.flooringmastery.controller.FlooringMasterController;
import com.flooringmastery.dao.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) throws FileNotFoundException {

        // Pass application context with spring
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasterController controller = applicationContext.getBean("controller", FlooringMasterController.class);

        // Main program loop
        controller.run();
    }
}
