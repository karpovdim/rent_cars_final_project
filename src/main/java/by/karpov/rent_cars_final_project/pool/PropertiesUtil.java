package by.karpov.rent_cars_final_project.pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    public static final String PATH_PROPERTIES = "application.properties";
    static {
        try (final var inputStream = PropertiesUtil.class
                .getClassLoader()
                .getResourceAsStream(PATH_PROPERTIES)) {
            PROPERTIES.load(inputStream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getProperty(String key) {
        final var property = PROPERTIES.getProperty(key);
        return property;
    }
}