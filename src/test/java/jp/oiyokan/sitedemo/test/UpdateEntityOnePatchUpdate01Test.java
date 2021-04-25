package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class UpdateEntityOnePatchUpdate01Test {
    private static final Log log = LogFactory.getLog(UpdateEntityOnePatchUpdate01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.getClient().getObjectFactory().newPrimitiveProperty("Name", SitedemoTestUtil
                .getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("Updated valu555")));

        final boolean isUpdated = SitedemoTestUtil.patchUpdateEntryOne("ODataTests7", Integer.valueOf(503), properties);
        if (isUpdated) {
            log.info("patch update success.");
        } else {
            log.info("patch update fail. no record deleted.");
        }
    }
}
