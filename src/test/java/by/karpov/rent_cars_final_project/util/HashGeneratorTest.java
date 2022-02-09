package by.karpov.rent_cars_final_project.util;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class HashGeneratorTest {

    CodeGenerator codeGenerator;
    String REGEX_CODE = "^\\d{4}$";

    @BeforeClass
    public void beforeClass() {
        codeGenerator = CodeGenerator.getInstance();
    }

    @Test
    public void generateCodeToRegistrationTest() {
        String actualCode = codeGenerator.generateCodeToRegistration();
        Assert.assertTrue(actualCode.matches(REGEX_CODE));
    }

    @AfterClass
    public void afterClass() {
        codeGenerator = null;
        REGEX_CODE = null;
    }
}