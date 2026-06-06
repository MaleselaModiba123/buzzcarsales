package za.ac.cput;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Smoke test: verifies the full Spring application context loads — all beans
 * wire up, entities map, and no configuration is broken. Runs against an
 * in-memory H2 database (see src/test/resources/application.properties).
 */
@SpringBootTest
class BuzzCarSalesApplicationTests {

    @Test
    void contextLoads() {
    }
}
