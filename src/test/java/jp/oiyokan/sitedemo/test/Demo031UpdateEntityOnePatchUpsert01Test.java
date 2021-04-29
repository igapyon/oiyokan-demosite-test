package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo031UpdateEntityOnePatchUpsert01Test {
    private static final Log log = LogFactory.getLog(Demo031UpdateEntityOnePatchUpsert01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.newPropertyString("Name", "Updated valu555"));

        SitedemoTestUtil.patchUpsertEntryOne("ODataTest7", Integer.valueOf(50201), properties);
        log.info("UPSERT成功");

        // TODO デフォルトは、204ではなくって200で内容返却???
    }
}
