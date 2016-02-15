package alex.test.controller.validation;

public class PhoneValidator {
    public static final String REGEX_FOR_VALIDATE_PURIFIED = "^(00)?([0-9]{3})?[0-9]{9}$";
    public static final String REGEX_FOR_CLEARING_RAW = "-|\\)|\\(|\\+| ";

    public static boolean notValid(String phone) {
        return !prepare(phone).matches(REGEX_FOR_VALIDATE_PURIFIED);
    }

    public static String prepare(String rawPhone) {
        return rawPhone.replaceAll(REGEX_FOR_CLEARING_RAW, "");
    }
}
