package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class CreateEntityOnePost01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(CreateEntityOnePost01Test.class);

    @Test
    void test01() {
        // 自動採番にて何度でも動作可能.
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.getClient().getObjectFactory().newPrimitiveProperty("Name", SitedemoTestUtil
                .getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("Updated valu555")));

        SitedemoTestUtil.createEntityOne("ODataTests1", properties);
    }
}
