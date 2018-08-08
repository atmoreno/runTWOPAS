package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Resources {

    public static Resources INSTANCE;

    private final Properties properties;

    private Resources(Properties properties){this.properties = properties;}

    public static void initializeResources(String fileName){
        try (FileInputStream in = new FileInputStream(fileName)){
            Properties properties = new Properties();
            properties.load(in);
            INSTANCE = new Resources(properties);
            in.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public synchronized int getInt(String key) {
        return Integer.parseInt(properties.getProperty(key));
    }

    public synchronized String getString(String key) {
        return properties.getProperty(key);
    }

    public synchronized String[] getArray(String key) {
        return properties.getProperty(key).split(",");
    }

    public synchronized boolean getBoolean(String key) {
        return Boolean.parseBoolean(properties.getProperty(key));
    }

    public synchronized boolean getBoolean(String key, boolean defaultValue) {
        if (properties.containsKey(key)) {
            return Boolean.parseBoolean(properties.getProperty(key));
        } else {
            return defaultValue;
        }
    }

    public synchronized double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);
        return value != null ? Double.parseDouble(value) : defaultValue;
    }

}
