package jp.oiyokan.sitedemo.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class CreateEntityOnePost02Test {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(CreateEntityOnePost02Test.class);

    @Test
    void test01() {
        // 一度動作させると以降はエラー.
        final List<ClientProperty> properties = new ArrayList<>();
        properties.add(SitedemoTestUtil.getClient().getObjectFactory().newPrimitiveProperty("Name", SitedemoTestUtil
                .getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("Updated valu555")));

        // TODO FIXME 409でほんとにいいのか？400ではないのか？を検討。

        SitedemoTestUtil.createEntityOne("ODataTests7", properties);
    }
}
