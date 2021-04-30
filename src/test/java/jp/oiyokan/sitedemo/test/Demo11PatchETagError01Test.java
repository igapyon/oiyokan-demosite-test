package jp.oiyokan.sitedemo.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.communication.ODataClientErrorException;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo11PatchETagError01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo11PatchETagError01Test.class);

    @Test
    void test01() {
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.newPropertyString("Name", "Updated NewVAL"));

        try {
            SitedemoErrorTestUtil.patchETag4Error("ODataTest7", Integer.valueOf(50203), properties, true, false);
            assertTrue(false, "ここにきたらエラー");
        } catch (ODataClientErrorException ex) {
            assertEquals(400, ex.getStatusLine().getStatusCode());
            assertTrue(ex.getMessage().contains("[IY3109]"));
        }
    }
}
