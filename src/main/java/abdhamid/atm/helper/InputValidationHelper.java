package abdhamid.atm.helper;

import java.util.Objects;

public class InputValidationHelper {
    public static String validateOption(String option, int min, int max) {
        if (option.isEmpty()) {
            System.out.println("Command can't be empty");
        }
        else if (!option.matches("\\d+") || Integer.parseInt(option) < min || Integer.parseInt(option) > max) {
            System.out.println("Invalid command");
        }
        else return option;
        return null;
    }

    public static String validateTransferAmount(String amount, int min, int max) {
        if (!amount.matches("\\d+") || Integer.parseInt(amount)%10 != 0) {
            System.out.println("Invalid amount");
        }
        else if (Integer.parseInt(amount) > max) {
            System.out.println("Maximum amount to transfer is $1000");
        }
        else if (Integer.parseInt(amount) < min) {
            System.out.println("Minimum amount to transfer is $1");
        }
        else return amount;

        return null;
    }

    public static String validateAccount(String input, String type) {
        if (input.length() != 6) {
            if (Objects.equals(type, "NUMBER")) {
                System.out.println("Account Number should have 6 digits length");
            } else {
                System.out.println("PIN should have 6 digits length");
            }
        }
        else if (input.matches("[a-zA-Z]+")) {
            if (Objects.equals(type, "NUMBER")) {
                System.out.println("Account Number should only contains numbers");
            } else {
                System.out.println("PIN should only contains numbers");
            }
        }
        else return input;
        return null;
    }

}
