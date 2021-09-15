package config;

public class Configuration {

    private static final DPTestConfig defaults = new DPTestConfig();

    public static String METADATA_HOSTNAME = defaults.metadataHostname();

    public static int METADATA_PORT = defaults.metadataPort();

    public static int METADATA_TIMEOUT = defaults.metadataTimeout();

    public static String ZEPPELIN_HOSTNAME = defaults.zeppelinHostname();

    public static int ZEPPELIN_PORT = defaults.zeppelinPort();

}
