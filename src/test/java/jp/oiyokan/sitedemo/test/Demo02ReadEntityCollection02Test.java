package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.junit.jupiter.api.Test;

public class Demo02ReadEntityCollection02Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo02ReadEntityCollection02Test.class);

    @Test
    void test01() {
        @SuppressWarnings("unused")
        final ClientEntitySet entitySet = SitedemoTestUtil.readEntityCollection("ODataTest1", true,
                "ID,Name,DateTimeOffset1", "ID ge 200", "ID desc", 3, 0);
    }
}
