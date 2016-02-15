package alex.test.service;

import alex.test.domain.UserPhone;
import alex.test.service.util.UserPhoneServiceUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class UserPhoneService {
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
            UserPhoneServiceUtil.addLineSeparator(fileForSave, writer);
            writer.write(UserPhoneServiceUtil.prepareRecord(userPhone));
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException("For: " + userPhone, e);
        }
        return userPhone;
    }

    private File getFileForSave(UserPhone userPhone) {
        String targetFileName = UserPhoneServiceUtil.prepareFileName(userPhone);
        return Paths.get(phoneDirectory, targetFileName).toFile();
    }
}
