package abdhamid.atm.helper;

import java.util.Random;

public class RefIdHelper {
    public static int generateRefId() {
        Random rnd = new Random();
        return rnd.nextInt(999999);
    }
}
