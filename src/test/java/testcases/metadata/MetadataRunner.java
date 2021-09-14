package testcases.metadata;

import com.intuit.karate.junit5.Karate;

public class MetadataRunner {

    @Karate.Test
    Karate runTests() {
        return Karate.run("cluster-operations")
                .relativeTo(getClass());
    }

}
