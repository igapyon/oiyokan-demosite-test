package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class UpdateEntityOnePatchUpsert01Test {
    private static final Log log = LogFactory.getLog(UpdateEntityOnePatchUpsert01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.getClient().getObjectFactory().newPrimitiveProperty("Name", SitedemoTestUtil
                .getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("Updated valu555")));

        SitedemoTestUtil.patchUpsertEntryOne("ODataTests7", Integer.valueOf(5020), properties);
        log.info("UPSERT成功");
    }
}
