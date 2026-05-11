import com.flooringmastery.controller.FlooringMasterController;
import com.flooringmastery.dao.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;

public class App {
    public static void main(String[] args) throws FileNotFoundException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasterController controller = applicationContext.getBean("controller", FlooringMasterController.class);
        controller.run();
    }
}
