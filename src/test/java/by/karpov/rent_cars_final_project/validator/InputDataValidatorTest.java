package by.karpov.rent_cars_final_project.validator;

import by.karpov.rent_cars_final_project.entity.User;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class InputDataValidatorTest {
    InputDataValidator validator;
    User user = User.builder()
            .role(User.UserRole.ADMIN)
            .build();

    @BeforeClass
    public void initialize() {
        validator = InputDataValidator.getInstance();
    }

    @Test
    public void testIsUserValid() {
    }

    @Test
    public void testIsIdValid() {
    }

    @DataProvider()
    public Object[][] createDataToEmailValidator() {
        return new Object[][] {
                { "korozarecipient@gmail.com", true },
                { "1@1.1", false },
                { " ", false },
                { null, false } };
    }
    @Test(description = "Testing email validator", dataProvider = "createDataToEmailValidator")
    public void isEmailValidTest(String email, boolean expected) {
        boolean actual = validator.isEmailValid(email);
        assertEquals(actual, expected);
    }
    @DataProvider
    public Object[][] createDataToPasswordValidator() {
        return new Object[][] {
                {"qwe11QQd!!", true},
                {"1", false},
                {" ", false},
                {"", false},
                {null, false} };
    }

    @Test(description = "Testing password validator", dataProvider = "createDataToPasswordValidator")
    public void isPasswordValidTest(String password, boolean expected) {
        boolean actual = validator.isPasswordValid(password);
        assertEquals(actual, expected);
    }
    @DataProvider
    public Object[][] createDataToNameValidator() {
        return new Object[][] {
                {"Max", true},
                {"Валера", true},
                {" ", false},
                {"", false},
                {null, false} };
    }

    @Test(description = "Testing name validator", dataProvider = "createDataToNameValidator")
    public void isNameValidTest(String name, boolean expected) {
        boolean actual = validator.isNameValid(name);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] createDataToPhoneNumberValidator() {
        return new Object[][] {
                {"448561486", true},
                {"111111111", false},
                {"2511Q1111", false},
                {"25111111", false},
                {"0293283829", false},
                {"375293283829", false},
                {" ", false},
                {"", false},
                {null, false} };
    }

    @Test(description = "Testing pnone number validator", dataProvider = "createDataToPhoneNumberValidator")
    public void isPhoneNumberValidTest(String password, boolean expected) {
        boolean actual = validator.isPhoneNumberValid(password);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] createDataToCardNumberValidator() {
        return new Object[][] {
                {"1234123412341234", true},
                {"111111111", false},
                {"2511Q1111", false},
                {" ", false},
                {"", false},
                {null, false} };
    }

    @Test(description = "Testing card number validator", dataProvider = "createDataToCardNumberValidator")
    public void isCardNumberValidTest(String cardNumber, boolean expected) {
        boolean actual = validator.isCardNumberValid(cardNumber);
        assertEquals(actual, expected);
    }

    @DataProvider
    public Object[][] createDataToCvvValidator() {
        return new Object[][] {
                {"123", true},
                {"111111111", false},
                {"2a1", false},
                {" ", false},
                {"", false},
                {null, false} };
    }

    @Test(description = "Testing cvv validator", dataProvider = "createDataToCvvValidator")
    public void isCvvValidTest(String cvv, boolean expected) {
        boolean actual = validator.isCvvValid(cvv);
        assertEquals(actual, expected);
    }

    @Test
    public void testIsAdmin() {
        boolean actual = validator.isAdmin(user);
        assertTrue(actual);
    }

    @Test
    public void testIsCodeValid() {
    }

    @Test
    public void testIsStatusUserPresent() {
    }

    @Test
    public void testIsRolePresent() {
    }

    @Test
    public void testIsActiveUser() {
    }

    @Test
    public void testIsActiveAdmin() {
    }

    @Test
    public void testIsStatusCarPresent() {
    }

    @Test
    public void testIsTransmissionCarPresent() {
    }

    @Test
    public void testIsCostValid() {
    }

    @Test
    public void testIsDescriptionValid() {
    }

    @Test
    public void testIsRegNumberValid() {
    }

    @Test
    public void testIsCarPresent() {
    }

    @Test
    public void testIsStatusOrderPresent() {
    }
}