package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class UpdateEntityOnePatchInsert01Test {
    private static final Log log = LogFactory.getLog(UpdateEntityOnePatchInsert01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();

        properties.add(SitedemoTestUtil.getClient().getObjectFactory().newPrimitiveProperty("Name", SitedemoTestUtil
                .getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("Updated valu555")));

        final boolean isInserted = SitedemoTestUtil.patchInsertEntryOne("ODataTests7", Integer.valueOf(503),
                properties);
        if (isInserted) {
            log.info("patch insert success.");
        } else {
            log.info("patch insert fail. no record deleted.");
        }

    }
}
