package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo033UpdateEntityOnePatchInsert01Test {
    private static final Log log = LogFactory.getLog(Demo033UpdateEntityOnePatchInsert01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();

        properties.add(SitedemoTestUtil.newPropertyString("Name", "Updated valu555"));

        final boolean isInserted = SitedemoTestUtil.patchInsertEntryOne("ODataTest7", Integer.valueOf(50111),
                properties);
        if (isInserted) {
            log.info("patch insert success.");
        } else {
            log.warn("patch insert fail. no record inserted.");
        }

    }
}
