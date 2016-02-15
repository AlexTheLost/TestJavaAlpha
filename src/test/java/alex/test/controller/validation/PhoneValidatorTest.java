package alex.test.controller.validation;

import org.junit.Assert;
import org.junit.Test;


public class PhoneValidatorTest {

    @Test
    public void testValid() throws Exception {

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
        Assert.assertTrue(PhoneValidator.bracketsLeftEqualRight("()"));
        Assert.assertTrue(PhoneValidator.bracketsLeftEqualRight("()()"));
        Assert.assertTrue(PhoneValidator.bracketsLeftEqualRight("(())"));
    }
}