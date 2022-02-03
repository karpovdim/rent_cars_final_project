package by.karpov.rent_cars_final_project.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CodeGenerator {

    private static final Logger LOGGER = LogManager.getLogger(CodeGenerator.class);


    private static final CodeGenerator INSTANCE = new CodeGenerator();

    private static final int MIN_BORDER_TO_REGISTRATION = 1000;

    private static final int MAX_BORDER_TO_REGISTRATION = 9999;

    private CodeGenerator() {
    }

    public static CodeGenerator getInstance() {
        return INSTANCE;
    }

    public String generateCodeToRegistration() {
        LOGGER.info( "method generateCode()");
        int code = (int) (Math.random() * (MAX_BORDER_TO_REGISTRATION - MIN_BORDER_TO_REGISTRATION)) + MIN_BORDER_TO_REGISTRATION;
        return String.valueOf(code);
    }
}
