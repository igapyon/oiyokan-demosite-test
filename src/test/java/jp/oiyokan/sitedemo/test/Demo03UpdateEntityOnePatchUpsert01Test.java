package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo03UpdateEntityOnePatchUpsert01Test {
    private static final Log log = LogFactory.getLog(Demo03UpdateEntityOnePatchUpsert01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.newPropertyString("Name", "Updated NewVAL"));

        final ClientEntity entity = SitedemoTestUtil.patchUpsertEntryOne("ODataTest7", Integer.valueOf(50203),
                properties);
        if (entity != null) {
            log.info("UPSERT成功");
        } else {
            log.error("UPSERT失敗");
        }
    }
}
