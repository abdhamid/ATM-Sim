package abdhamid.atm;

import abdhamid.atm.controller.ApplicationController;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.format.DateTimeFormatter;
import java.util.*;


@SpringBootApplication
class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");

        ApplicationController applicationController = new ApplicationController();
        while (true) {
            applicationController.welcomeScreen(scanner);
            break;
        }
    }
}