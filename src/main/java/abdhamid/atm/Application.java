package abdhamid.atm;

import abdhamid.atm.controller.ApplicationController;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.*;


@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ApplicationController applicationController = new ApplicationController();
        while (true) {
            applicationController.welcomeScreen(scanner);
            break;
        }
    }
}