package abdhamid.atm;

import abdhamid.atm.controller.CLIApplicationController;
import org.springframework.boot.SpringApplication;
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
        SpringApplication.run(Application.class);
    }
//
//    CLI version
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        CLIApplicationController CLIApplicationController = new CLIApplicationController();
//        while (true) {
//            CLIApplicationController.welcomeScreen(scanner);
//            break;
//        }
//    }
}