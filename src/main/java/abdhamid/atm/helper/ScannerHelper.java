package abdhamid.atm.helper;

import java.util.Scanner;


public class ScannerHelper {
    static final int MAX_TRANSFER = 1000;
    static final int MIN_TRANSFER = 1;

    public static String customerScanner(Scanner scanner) {

        String destAccountNum = null;
        while (destAccountNum == null) {
            System.out.print("""

                        Please enter destination account and\s
                        press enter to continue :\s""");
            destAccountNum = scanner.nextLine();
            destAccountNum = InputValidationHelper.validateAccount(destAccountNum, "NUMBER");
        }
        return destAccountNum;
    }

    public static Integer amountScanner(Scanner scanner) {
        String transferAmountInput = null;
        while (transferAmountInput == null) {
            System.out.print("Please enter transfer amount and press enter to continue : ");
            String input = scanner.nextLine();
            transferAmountInput = InputValidationHelper.validateTransferAmount(input, MIN_TRANSFER, MAX_TRANSFER);
        }
        return Integer.parseInt(transferAmountInput);
    }
}
