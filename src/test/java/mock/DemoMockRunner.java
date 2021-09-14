package mock;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.core.MockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.springframework.test.util.AssertionErrors.assertTrue;

public class DemoMockRunner {

    static MockServer mockServer;

    @BeforeAll
    public static void setup() {
        mockServer = MockServer.feature("classpath:mock/demo-mock.feature")
                .http(0)
                .build();
    }

    @AfterAll
    public static void teardown() {
        mockServer.stop();
    }

    @Test
    public void testParallel() throws IOException {
        Results results = Runner.path("classpath:driver")
                .configDir("classpath:mock")
                .systemProperty("demo.server.port", mockServer.getPort() + "")
                .systemProperty("demo.server.https", "false")
                .parallel(1);

        System.out.println("hello world!");

        assertTrue(results.getErrorMessages(), results.getFailCount() == 0);
    }

}