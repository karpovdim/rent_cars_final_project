package by.karpov.rent_cars_final_project.util;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CodeGeneratorTest {

    @Test(description = "Testing generate password hash")
    public void generatePasswordHashTest() {
        String expected = "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5";
        HashGenerator hashGenerator = HashGenerator.getInstance();
        String actual = hashGenerator.generatePasswordHash("12345");
        assertEquals(expected, actual);
    }
}