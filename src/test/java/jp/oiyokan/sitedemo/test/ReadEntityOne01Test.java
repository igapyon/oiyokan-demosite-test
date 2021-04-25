package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

public class ReadEntityOne01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(ReadEntityOne01Test.class);

    @Test
    void test01() {
        SitedemoTestUtil.readEntityOne("ODataTests1", Integer.valueOf(1));
    }
}
