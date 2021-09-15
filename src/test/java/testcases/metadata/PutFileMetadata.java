package testcases.metadata;

import common.MDClientHolder;
import config.Configuration;
import lib.metadata.FileMetadata;

import mock.thrift.MetadataGenerator;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;
import org.junit.jupiter.api.*;

import util.ParamGenerator;
import util.StringUtil;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PutFileMetadata {

    private MDClientHolder holder = null;
    private boolean isSuccess = false;

    private String dbName = ParamGenerator.getDbName(getClass());
    private String tblName = ParamGenerator.getTblName(getClass());
    private String filename = ParamGenerator.getFilename(0);

    private FileMetadata excepted = MetadataGenerator.getMockFileMetadata();

    @BeforeAll
    public void setup() throws TTransportException {
        holder = MDClientHolder.builder()
                .hostname(Configuration.METADATA_HOSTNAME)
                .port(Configuration.METADATA_PORT)
                .timeout(Configuration.METADATA_TIMEOUT)
                .build();
    }

    @Test
    public void testNormal() throws TException {
        holder.getClient().putFileMetadata(dbName, tblName, filename, excepted);

        FileMetadata result
                = holder.getClient().getFileMetadata(dbName, tblName, filename);
        Assertions.assertEquals(excepted, result,
                "");
    }

    @Test
    public void testWithInvalidReq01() throws TException {
        try {
            holder.getClient().putFileMetadata(null, tblName, filename, excepted);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.InvalidRequestException",
                    "");
        }
    }

    @Test
    public void testWithInvalidReq02() throws TException {
        try {
            holder.getClient().putFileMetadata(dbName, null, filename, excepted);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.InvalidRequestException",
                    "");
        }
    }

    @Test
    public void testWithInvalidReq03() throws TException {
        try {
            holder.getClient().putFileMetadata(dbName, tblName, null, excepted);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.InvalidRequestException",
                    "");
        }
    }

    @Test
    public void testWithInvalidReq04() throws TException {
        try {
            holder.getClient().putFileMetadata(dbName, tblName, filename, null);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.InvalidRequestException",
                    "");
        }
    }

    @Test
    public void testFileExists() throws TException {
        FileMetadata metadata = MetadataGenerator.getMockFileMetadata();
        try {
            holder.getClient().putFileMetadata(dbName, tblName, filename, metadata);
        }
        catch (TApplicationException ex) {
            Assertions.assertEquals(StringUtil.getMetadataErrMsg(ex.getMessage()),
                    "Metadata.API.DataAccessException",
                    "");
        }
    }

    @AfterAll
    public void tearDown() {
        if (null != holder) {
            holder.close();
        }
    }

}
