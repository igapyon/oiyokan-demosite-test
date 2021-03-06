package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo01CreateEntityOnePost01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo01CreateEntityOnePost01Test.class);

    @Test
    void test01() {
        // 自動採番にて何度でも動作可能.
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.newPropertyString("Name", "Created Demo011CreateEntityOnePost01Test"));

        @SuppressWarnings("unused")
        final ClientEntity entity = SitedemoTestUtil.createEntityOne("ODataTest1", properties);
    }
}
