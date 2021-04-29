package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

public class Demo023ReadEntityOne01Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo023ReadEntityOne01Test.class);

    @Test
    void test01() {
        SitedemoTestUtil.readEntityOne("ODataTest1", Integer.valueOf(1));
    }
}
