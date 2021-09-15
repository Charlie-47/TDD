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
public class GetFileMetadata {

    private String dbName = ParamGenerator.getDbName(getClass());
    private String tblName = ParamGenerator.getTblName(getClass());
    private String fileName = ParamGenerator.getFilename(0);

    private MDClientHolder holder;
    private boolean isSuccess = false;

    private FileMetadata excepted = MetadataGenerator.getMockFileMetadata();

    @BeforeAll
    public void setup() throws TException {
        holder = MDClientHolder.builder()
                .hostname(Configuration.METADATA_HOSTNAME)
                .port(Configuration.METADATA_PORT)
                .timeout(Configuration.METADATA_TIMEOUT)
                .build();

        holder.getClient().putFileMetadata(dbName, tblName, fileName, excepted);
    }

    @Test
    public void testNormal() throws TException {
        FileMetadata result = holder.getClient().getFileMetadata(dbName, tblName, fileName);

        Assertions.assertEquals(excepted, result,
                "inserted data is inconsistent with obtained data.");
    }

    @Test
    public void testWithInvalidReq01() throws TException {
        try {
            holder.getClient().getFileMetadata(null, tblName, fileName);
        }
        catch (TApplicationException ex) {
            // TODO: check exception
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.InvalidRequestException");
        }
    }

    @Test
    public void testWithInvalidReq02() throws TException {
        try {
            holder.getClient().getFileMetadata(dbName, null, fileName);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.InvalidRequestException");
        }
    }

    @Test
    public void testWithInvalidReq03() throws TException {
        try {
            holder.getClient().getFileMetadata(dbName, tblName, null);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.InvalidRequestException");
        }
    }

    @Test
    public void testFileNotExists() throws TException {
        try {
            holder.getClient().getFileMetadata(dbName + ".bak", tblName + ".bak",
                    fileName + ".bak");
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.NoObjectException");
        }
    }

    // TODO: do we need to clean up the data?
    @AfterAll
    public void tearDown() {
        if (null != holder) {
            holder.close();
        }
    }

}
