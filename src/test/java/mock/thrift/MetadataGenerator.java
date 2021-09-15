package mock.thrift;

import lib.metadata.FileMetadata;
import lib.metadata.FormatType;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MetadataGenerator {

    private static final Random RANDOM = new Random();
    private static int _KB = 1024;

    public static FileMetadata getMockFileMetadata() {
        FileMetadata fileMetadata = new FileMetadata();
        fileMetadata.setFile_format(FormatType.PARQUET);

        int recordCount = RANDOM.nextInt(10000);
        fileMetadata.setRecord_count(recordCount);
        fileMetadata.setFile_size_in_bytes(recordCount * 128 * _KB);

        int columnCount = 10 + RANDOM.nextInt(10);
        fileMetadata.setColumn_sizes(getMockColumnInfo(columnCount));
        fileMetadata.setValue_counts(getMockColumnInfo(columnCount));
        fileMetadata.setNull_value_counts(getMockColumnInfo(columnCount));
        fileMetadata.setNan_value_counts(getMockColumnInfo(columnCount));

        fileMetadata.setLower_bounds(getMockBoundaries(columnCount));
        fileMetadata.setUpper_bounds(getMockBoundaries(columnCount));

        fileMetadata.setKey_metadata(UUID.randomUUID().toString().getBytes());

        List<Long> splitOffsets = new ArrayList<>();
        fileMetadata.setSplit_offsets(splitOffsets);

        fileMetadata.setSort_order_id(RANDOM.nextInt(Integer.MAX_VALUE));

        return fileMetadata;
    }

    private static Map<Integer, Long> getMockColumnInfo(int columnCount) {
        Map<Integer, Long> map = new HashMap<>();
        for (int i = 0; i < columnCount; i++) {
            map.put(i, (long) RANDOM.nextInt(Integer.MAX_VALUE));
        }
        return map;
    }

    private static Map<Integer, ByteBuffer> getMockBoundaries(int columnCount) {
        Map<Integer, ByteBuffer> map = new HashMap<>();
        for (int i = 0; i < columnCount; i++) {
            map.put(i, ByteBuffer.wrap(UUID.randomUUID()
                    .toString().getBytes(StandardCharsets.UTF_8)));
        }
        return map;
    }

}
