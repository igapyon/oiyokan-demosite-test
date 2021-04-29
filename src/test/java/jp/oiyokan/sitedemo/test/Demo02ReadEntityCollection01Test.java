package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.junit.jupiter.api.Test;

public class Demo02ReadEntityCollection01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo02ReadEntityCollection01Test.class);

    @Test
    void test01() {
        @SuppressWarnings("unused")
        final ClientEntitySet entitySet = SitedemoTestUtil.readEntityCollection("ODataTest1", true,
                "ID,Name,DateTimeOffset1", "ID le 4", "ID", 10, 0);
    }
}
