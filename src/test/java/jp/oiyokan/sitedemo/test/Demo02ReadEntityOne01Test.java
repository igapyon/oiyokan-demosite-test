package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.junit.jupiter.api.Test;

public class Demo02ReadEntityOne01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo02ReadEntityOne01Test.class);

    @Test
    void test01() {
        @SuppressWarnings("unused")
        final ClientEntity entity = SitedemoTestUtil.readEntityOne("ODataTest1", Integer.valueOf(1));
    }
}
