package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo01CreateEntityOnePost02Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo01CreateEntityOnePost02Test.class);

    @Test
    void test01() {
        // 一度動作させると以降はエラー.
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.newPropertyInt32("ID", 3323));
        properties.add(SitedemoTestUtil.newPropertyString("Name", "Updated valu555"));

        final ClientEntity entity = SitedemoTestUtil.createEntityOne("ODataTest7", properties);
        if (entity != null) {
            log.info("insert success.");
        } else {
            log.warn("insert fail. no record inserted.");
        }
    }
}
