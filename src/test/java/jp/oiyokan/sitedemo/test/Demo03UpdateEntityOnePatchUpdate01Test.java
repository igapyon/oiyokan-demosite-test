package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo03UpdateEntityOnePatchUpdate01Test {
    private static final Log log = LogFactory.getLog(Demo03UpdateEntityOnePatchUpdate01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.newPropertyString("Name", "Updated valu555"));

        final boolean isUpdated = SitedemoTestUtil.patchUpdateEntryOne("ODataTest7", Integer.valueOf(50201),
                properties);
        if (isUpdated) {
            log.info("patch update success.");
        } else {
            log.warn("patch update fail. no record modified.");
        }
        // TODO デフォルトは、204ではなくって200で内容返却???

    }
}