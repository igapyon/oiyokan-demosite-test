package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.junit.jupiter.api.Test;

public class Demo00ReadOiyokanVersionTest {
    private static final Log log = LogFactory.getLog(Demo00ReadOiyokanVersionTest.class);

    @Test
    void test01() {
        final ClientEntity entity = SitedemoTestUtil.readEntityOne("Oiyokan", "Version");
        log.debug("Oiyokan ver: " + entity.getProperty("KeyValue").getValue());
    }
}
