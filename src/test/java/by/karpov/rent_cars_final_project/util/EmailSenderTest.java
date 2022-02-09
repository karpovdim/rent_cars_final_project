package by.karpov.rent_cars_final_project.util;

import by.karpov.rent_cars_final_project.exception.ServiceException;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import static org.testng.Assert.*;

public class EmailSenderTest {

    EmailSender emailSender;

    @BeforeClass
    public void initialize() {
        emailSender = EmailSender.getInstance();
    }

    @DataProvider()
    public Object[][] createData() {
        return new Object[][] { { "korozarecipient@gmail.com", true }, { "", false }, { " ", false }, { null, false } };
    }

    @Test(description = "Testing method sendMail", dataProvider = "createData")
    public void sendMailTest(String recipient, boolean expected) throws ServiceException, com.google.protobuf.ServiceException {
        String code = "TEST";
        boolean actual = emailSender.sendMail(recipient, code);
        assertEquals(actual, expected);
    }

    @AfterClass
    public void tierDown() {
        emailSender = null;
    }
}