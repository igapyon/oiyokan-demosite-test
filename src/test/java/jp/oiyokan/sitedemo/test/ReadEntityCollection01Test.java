package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

public class ReadEntityCollection01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ReadEntityCollection01Test.class);

    @Test
    void test01() {
        SitedemoTestUtil.readEntityCollection("ODataTests1", true, "ID,Name,DateTimeOffset1", "ID le 4", "ID", 10, 0);
    }
}
