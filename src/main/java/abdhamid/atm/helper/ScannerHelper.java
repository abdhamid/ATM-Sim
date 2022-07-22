package abdhamid.atm.helper;

import java.util.Scanner;


public class ScannerHelper {
    static final int MAX_TRANSFER = 1000;
    static final int MIN_TRANSFER = 1;

    public static String customerScanner(Scanner scanner, String menu) {

        String destAccountNum = null;
        while (destAccountNum == null) {
            System.out.println(menu);
            destAccountNum = scanner.nextLine();
            destAccountNum = InputValidationHelper.validateAccount(destAccountNum, "NUMBER");
        }
        return destAccountNum;
    }

    public static Integer amountScanner(Scanner scanner, String menu) {
        String transferAmountInput = null;
        while (transferAmountInput == null) {
            System.out.println(menu);
            String input = scanner.nextLine();
            transferAmountInput = InputValidationHelper.validateTransferAmount(input, MIN_TRANSFER, MAX_TRANSFER);
        }
        return Integer.parseInt(transferAmountInput);
    }

    public static Integer optionScanner(Scanner scanner, int min, int max, String menu) {
        String option = null;
        while (option == null) {
            System.out.println(menu);
            String input = scanner.nextLine();
            option = InputValidationHelper.validateOption(input, min, max);
        }
        return Integer.parseInt(option);
    }
}
