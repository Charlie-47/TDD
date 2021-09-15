package util;

import lib.metadata.FileMetadata;

import java.util.UUID;

public class ParamGenerator {

    public static String getDbName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase() + ".db";
    }

    public static String getTblName(Class<?> clazz) {
        return clazz.getSimpleName().toLowerCase() + ".tbl";
    }

    public static String getFilename(int index) {
        return "part-0000" + index
                + "-" + UUID.randomUUID()
                + ".c000.snappy.parquet";
    }

    public static FileMetadata getMockFileMetadata() {

        return null;
    }

}
