package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo03UpdateEntityOnePatchInsert01Test {
    private static final Log log = LogFactory.getLog(Demo03UpdateEntityOnePatchInsert01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();

        properties.add(SitedemoTestUtil.newPropertyString("Name", "Updated valu555"));

        // TODO FIXME 204ではなく201を戻して欲しい。
        final ClientEntity entity = SitedemoTestUtil.patchInsertEntryOne("ODataTest7", Integer.valueOf(50114),
                properties);
        if (entity != null) {
            log.info("patch insert success.");
        } else {
            // 失敗コースは 409
            log.warn("patch insert fail. no record inserted.");
        }
    }
}
