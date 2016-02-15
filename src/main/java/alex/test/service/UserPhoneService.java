package alex.test.service;

import alex.test.controller.validation.PhoneValidator;
import alex.test.domain.UserPhone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
public class UserPhoneService {
    public static final int FULL_INTERNATIONAL_PREFIX = 5;
    public static final int SHORT_INTERNATIONAL_PREFIX = 3;
    public static final int LOCAL_PART = 9;
    private static final DateTimeFormatter HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Value("${phone_directory}")
    private String phoneDirectory;

    @PostConstruct
    private void init() {
        checkDirectoryAvailabilityAndCreateIfNeed();
    }

    private void checkDirectoryAvailabilityAndCreateIfNeed() {
        File directory = Paths.get(phoneDirectory).toFile();
        if (!directory.exists() && !directory.mkdir())
            throw new IllegalStateException("Can't create directory: " + phoneDirectory);
        if (!directory.canRead())
            throw new IllegalStateException("Don't have privileges for read: " + phoneDirectory);
        if (!directory.canWrite())
            throw new IllegalStateException("Don't have privileges for write: " + phoneDirectory);
    }

    public UserPhone save(UserPhone userPhone) {
        File fileForSave = getFileForSave(userPhone);
        try (FileWriter writer = new FileWriter(fileForSave, true)) {
            addLineSeparator(fileForSave, writer);
            writer.write(prepareRecord(userPhone));
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException("For: " + userPhone, e);
        }
        return userPhone;
    }

    private void addLineSeparator(File fileForSave, FileWriter writer) throws IOException {
        if (fileForSave.length() != 0)
            writer.append(System.lineSeparator());
    }

    private File getFileForSave(UserPhone userPhone) {
        String targetFileName = prepareFileName(userPhone);
        return Paths.get(phoneDirectory, targetFileName).toFile();
    }

    private String prepareFileName(UserPhone userPhone) {
        return (StringUtils.isEmpty(userPhone.getFirstName()) ? "ABSENT" : userPhone.getFirstName()) + "_" + userPhone.getLastName() + ".txt";
    }

    private String prepareRecord(UserPhone userPhone) {
        String rawPhoneNumber = PhoneValidator.prepare(userPhone.getNumber());
        String toStorePhoneNumber = normalized(rawPhoneNumber);
        String toStoreCreationTime = userPhone.getTime().format(HH_MM_SS);
        return (toStoreCreationTime + " " + toStorePhoneNumber).toUpperCase();
    }


    private String normalized(String rawPhone) {
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
