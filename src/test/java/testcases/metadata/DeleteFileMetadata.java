package testcases.metadata;

import common.MDClientHolder;
import config.Configuration;
import lib.metadata.FileMetadata;
import mock.thrift.MetadataGenerator;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.junit.jupiter.api.*;
import util.ParamGenerator;
import util.StringUtil;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteFileMetadata {

    private MDClientHolder holder = null;

    private static final String dbName = DeleteFileMetadata.class.getSimpleName();
    private static final String tblName = DeleteFileMetadata.class.getSimpleName();
    private static final String filename = ParamGenerator.getFilename(0);

    @BeforeAll
    public void setup() throws TException {
        holder = MDClientHolder.builder()
                .hostname(Configuration.METADATA_HOSTNAME)
                .port(Configuration.METADATA_PORT)
                .timeout(Configuration.METADATA_TIMEOUT)
                .build();

        FileMetadata fileMetadata = MetadataGenerator.getMockFileMetadata();
        holder.getClient().putFileMetadata(dbName, tblName, filename, fileMetadata);
    }

    @Test
    public void testNormal() throws TException {
        holder.getClient().deleteFileMetadata(dbName, tblName, filename);
    }

    @Test
    public void testWithInvalidReq01() throws TException {
        try {
            holder.getClient().deleteFileMetadata(null, tblName, filename);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals("Metadata.API.InvalidRequest",
                    StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "");
        }
    }

    @Test
    public void testWithInvalidReq02() throws TException {
        try {
            holder.getClient().deleteFileMetadata(dbName, null, filename);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals("Metadata.API.InvalidRequest",
                    StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "");
        }
    }

    @Test
    public void testWithInvalidReq03() throws TException {
        try {
            holder.getClient().deleteFileMetadata(dbName, tblName, null);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals("Metadata.API.InvalidRequest",
                    StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "");
        }
    }

    @Test
    public void testFileNotExists() throws TException {
        try {
            holder.getClient().deleteFileMetadata(dbName + ".bak", tblName + ".bak",
                    filename + ".bak");
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals("Metadata.API.DataAccessException",
                    StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "");
        }
    }

    @AfterAll
    public void teardown() {
        if (null != holder) {
            holder.close();
        }
    }

}
