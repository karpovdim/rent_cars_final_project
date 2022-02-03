package by.karpov.rent_cars_final_project.pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    public static final String PATH_PROPERTIES = "application.properties";
    static {
        try (InputStream inputStream = PropertiesUtil.class
                .getClassLoader()
                .getResourceAsStream(PATH_PROPERTIES)) {
            PROPERTIES.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);//TODO: CREATE CUSTOM EXCEPTION
        }
    }

    public static String getProperty(String key) {
        String property = PROPERTIES.getProperty(key);
        return property;
    }
}