import controller.ApplicationController;

import java.time.format.DateTimeFormatter;
import java.util.*;



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