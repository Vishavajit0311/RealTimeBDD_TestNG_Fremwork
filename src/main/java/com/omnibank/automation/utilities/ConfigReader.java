package com.omnibank.automation.utilities;

import com.omnibank.automation.exceptions.FrameworkException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties;

    static {
        try {
            String env = System.getProperty("env", "qa"); // defaults to qa if not passed
            String path = System.getProperty("user.dir") + "/config/" + env + ".properties";
            FileInputStream fis = new FileInputStream(path);
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            throw new FrameworkException("Properties file not found for the given environment", e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new FrameworkException("Key '" + key + "' not found in properties file");
        }
        return value;
    }
}
