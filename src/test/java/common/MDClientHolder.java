package common;

import lib.metadata.MetadataService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class MDClientHolder {

    private MetadataService.Client client;
    private TTransport transport;

    public MDClientHolder(MetadataService.Client client, TTransport transport) {
        this.client = client;
        this.transport = transport;
    }

    public MetadataService.Client getClient() {
        return this.client;
    }

    public void close() {
        if (null != transport) {
            transport.close();
        }
    }

    public static class Builder {

        private String hostname;
        private int port;
        private int timeout;

        public Builder hostname(String hostname) {
            this.hostname = hostname;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        // TODO: when you invoke build(), it will return a new client for testing to use.
        public MDClientHolder build() throws TTransportException {
            TTransport transport = new TSocket(hostname, port, timeout);
            TProtocol protocol = new TBinaryProtocol(transport);
            MetadataService.Client client = new MetadataService.Client(protocol);

            transport.open();

            return new MDClientHolder(client, transport);
        }

        @Override
        public String toString() {
            return "ClientBuilder{" +
                    "hostname='" + hostname + '\'' +
                    ", port=" + port +
                    ", timeout=" + timeout +
                    '}';
        }

    }

    public static Builder builder() {
        return new Builder();
    }

}
