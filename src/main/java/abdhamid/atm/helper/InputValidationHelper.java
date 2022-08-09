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
        if (!amount.matches("\\d+")) {
            return "Invalid amount";
        }
        else if (Integer.parseInt(amount)%10 != 0) {
            return "Amount must be in a multiple of 10";
        }
        else if (Integer.parseInt(amount) > max) {
            return "Maximum amount to transfer is $1000";
        }
        else if (Integer.parseInt(amount) < min) {
            return "Minimum amount to transfer is $1";
        }
        else return amount;

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

    public static String validateAccountNumber(String input) {
        if (input.length() != 6) {
                return "Account Number should have 6 digits length";
        }
        else if (input.matches("[a-zA-Z]+")) {
                return "Account Number should only contains numbers";
        }
        return "success";
    }

    public static String validateAccountPIN(String input) {
        if (input.length() != 6) {
            return "PIN should have 6 digits length";
        }
        else if (input.matches("[a-zA-Z]+")) {
            return "PIN should only contains numbers";
        }
        return "success";
    }

}
