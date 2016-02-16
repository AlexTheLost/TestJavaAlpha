package alex.test.service.util;

import alex.test.domain.UserPhone;
import org.junit.Assert;
import org.junit.Test;

import java.time.format.DateTimeFormatter;

public class UserPhoneServiceUtilTest {

    @Test
    public void testPrepareFileName() throws Exception {
        UserPhone userPhone = new UserPhone();

        String lastName = "lastName";
        userPhone.setLastName(lastName);

        String actual = UserPhoneServiceUtil.prepareFileName(userPhone);
        String expected = lastName.toUpperCase() + ".txt";

        Assert.assertEquals(expected, actual);

        String firstName = "firstName";
        userPhone.setFirstName(firstName);

        actual = UserPhoneServiceUtil.prepareFileName(userPhone);
        expected = (lastName + "_" + firstName).toUpperCase() + ".txt";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPrepareRecord() throws Exception {
        UserPhone userPhone = new UserPhone();

        String phone = "+(375)-(25)-(123-4567)";
        userPhone.setNumber(phone);

        String actual = UserPhoneServiceUtil.prepareRecord(userPhone);
        String expected = userPhone.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + " 00375251234567";

        Assert.assertEquals(expected, actual);
    }
}