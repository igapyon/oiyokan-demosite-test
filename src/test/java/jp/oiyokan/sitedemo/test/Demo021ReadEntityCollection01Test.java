package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

public class Demo021ReadEntityCollection01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo021ReadEntityCollection01Test.class);

    @Test
    void test01() {
        SitedemoTestUtil.readEntityCollection("ODataTest1", true, "ID,Name,DateTimeOffset1", "ID le 4", "ID", 10, 0);
    }
}
