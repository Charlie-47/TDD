package testcases.warehouse;

import com.intuit.karate.junit5.Karate;

public class VWHRunner {

    @Karate.Test
    Karate runTests() {
        return Karate.run("cluster-operations")
                .relativeTo(getClass());
    }

}
