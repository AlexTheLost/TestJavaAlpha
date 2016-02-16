package alex.test.controller.validation;

public class PhoneValidator {
    public static final String REGEX_FOR_VALIDATE_PURIFIED = "^(\\+|00)?([0-9]{3})?[0-9]{9}$";
    public static final String REGEX_FOR_CLEARING_RAW = "-| |\\(|\\)|\\+";

    public static boolean valid(String phone) {
        return bracketsLeftEqualRight(phone) && cleaning(phone).matches(REGEX_FOR_VALIDATE_PURIFIED);
    }

    /**
     * Step before validation, cleaning of noise symbols.
     */
    public static String cleaning(String rawPhone) {
        return rawPhone.replaceAll(REGEX_FOR_CLEARING_RAW, "");
    }

    public static boolean bracketsLeftEqualRight(String phoneNumber) {
        int counter = 0;
        for (char c : phoneNumber.toCharArray()) {
            if (c == '(') {
                counter++;
            }
            if (c == ')') {
                if (counter == 0) {
                    return false;
                }
                counter--;
            }
        }
        return counter == 0;
    }
}
