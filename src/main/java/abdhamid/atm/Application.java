package abdhamid.atm;

import abdhamid.atm.controller.ApplicationController;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;


@SpringBootApplication
class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ApplicationController applicationController = new ApplicationController();
        while (true) {
            applicationController.welcomeScreen(scanner);
            break;
        }
    }
}