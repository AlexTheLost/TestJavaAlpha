package alex.test.controller.validation;

import org.junit.Assert;
import org.junit.Test;


public class PhoneValidatorTest {

    @Test
    public void testValid() throws Exception {
        Assert.assertTrue(PhoneValidator.valid("+(420) 111 222 333"));
        Assert.assertTrue(PhoneValidator.valid("+(420)-111222333"));
        Assert.assertTrue(PhoneValidator.valid("+420111222333"));
        Assert.assertTrue(PhoneValidator.valid("00420111222333"));
        Assert.assertTrue(PhoneValidator.valid("(111) 222 (333)"));
        Assert.assertTrue(PhoneValidator.valid("123456789"));
    }

    @Test
    public void testPrepare() throws Exception {
        String actual = PhoneValidator.cleaning("(+375-25)(++()(000) (111) (333)-+");
        String expected = "37525000111333";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testBracketsLeftEqualRight() throws Exception {
        Assert.assertFalse(PhoneValidator.bracketsLeftEqualRight("("));
        Assert.assertFalse(PhoneValidator.bracketsLeftEqualRight(")"));
        Assert.assertFalse(PhoneValidator.bracketsLeftEqualRight(")("));
        Assert.assertFalse(PhoneValidator.bracketsLeftEqualRight("))(("));
        Assert.assertTrue(PhoneValidator.bracketsLeftEqualRight(""));
        Assert.assertTrue(PhoneValidator.bracketsLeftEqualRight("()"));
        Assert.assertTrue(PhoneValidator.bracketsLeftEqualRight("()()"));
        Assert.assertTrue(PhoneValidator.bracketsLeftEqualRight("(())"));
    }
}