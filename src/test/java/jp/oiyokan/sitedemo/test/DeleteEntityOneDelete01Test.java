package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

public class DeleteEntityOneDelete01Test {
    private static final Log log = LogFactory.getLog(DeleteEntityOneDelete01Test.class);

    @Test
    void test01() {
        final boolean isDeleted = SitedemoTestUtil.deleteEntryOne("ODataTests7", Integer.valueOf(50201));
        if (isDeleted) {
            log.info("delete success.");
        } else {
            log.warn("delete fail. no record deleted.");
        }
    }
}
