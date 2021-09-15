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
        return properties.getProperty("metadata.hostname", "metadata-server");
    }

    public int metadataPort() {
        return Integer.parseInt(properties.getProperty("metadata.port", "9090"));
    }

    public int metadataTimeout() {
        return Integer.parseInt(properties
                .getProperty("metadata.timeout", "3000"));
    }

    public String zeppelinHostname() {
        return properties.getProperty("zeppelin.hostname", "zeppelin-server");
    }

    public int zeppelinPort() {
        return Integer.parseInt(properties.getProperty("zeppelin.port", "80"));
    }

}
