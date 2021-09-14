package config;

import java.io.*;
import java.util.Properties;

public class DPTestConfig {

    private static Properties properties = null;

    static {
        properties = new Properties();
        InputStream in
                = DPTestConfig.class.getClassLoader().getResourceAsStream("test.properties");

        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String metadataHostname() {
        return properties.getProperty("metadata.hostname");
    }

    public int metadataPort() {
        return Integer.parseInt(properties.getProperty("metadata.port"));
    }

    public String zeppelinHostname() {
        return properties.getProperty("zeppelin.hostname");
    }

    public int zeppelinPort() {
        return Integer.parseInt(properties.getProperty("zeppelin.port"));
    }

}
