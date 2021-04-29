package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class Demo012CreateEntityOnePost02Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(Demo012CreateEntityOnePost02Test.class);

    @Test
    void test01() {
        // 一度動作させると以降はエラー.
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.newPropertyInt32("ID", 3320));
        properties.add(SitedemoTestUtil.newPropertyString("Name", "Updated valu555"));

        // TODO FIXME 409でほんとにいいのか？400ではないのか？を検討。

        SitedemoTestUtil.createEntityOne("ODataTest7", properties);
    }
}