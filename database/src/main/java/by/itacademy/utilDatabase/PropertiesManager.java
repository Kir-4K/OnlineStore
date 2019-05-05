package by.itacademy.utilDatabase;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesManager {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadApplicationProperties();
    }

    @SneakyThrows
    private static void loadApplicationProperties() {
        try (InputStream resource = PropertiesManager
                .class
                .getClassLoader()
                .getResourceAsStream("app")) {
            PROPERTIES.load(resource);
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
