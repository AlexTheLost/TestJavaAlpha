package alex.test.service.util;


import alex.test.controller.validation.PhoneValidator;
import alex.test.domain.UserPhone;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class UserPhoneServiceUtil {
    public static final int FULL_INTERNATIONAL_PREFIX = 5;
    public static final int SHORT_INTERNATIONAL_PREFIX = 3;
    public static final int LOCAL_PART = 9;
    private static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void addLineSeparator(File fileForSave, FileWriter writer) throws IOException {
        if (fileForSave.length() != 0)
            writer.append(System.lineSeparator());
    }

    public static String prepareFileName(UserPhone userPhone) {
        return (StringUtils.isEmpty(userPhone.getFirstName()) ? "ABSENT" : userPhone.getFirstName()) + "_" + userPhone.getLastName() + ".txt";
    }

    public static String prepareRecord(UserPhone userPhone) {
        String rawPhoneNumber = PhoneValidator.cleaning(userPhone.getNumber());
        String toStorePhoneNumber = normalized(rawPhoneNumber);
        String toStoreCreationTime = userPhone.getTime().format(HH_MM_SS);
        return (toStoreCreationTime + " " + toStorePhoneNumber).toUpperCase();
    }

    private static String normalized(String rawPhone) {
        if (rawPhone.length() == FULL_INTERNATIONAL_PREFIX + LOCAL_PART) {
            return rawPhone;
        } else if (rawPhone.length() == SHORT_INTERNATIONAL_PREFIX + LOCAL_PART) {
            return "00" + rawPhone;
        } else if (rawPhone.length() == LOCAL_PART) {
            return "00420" + rawPhone;
        }
        throw new IllegalStateException("Not valid lenght for phone: " + rawPhone);
    }
}
