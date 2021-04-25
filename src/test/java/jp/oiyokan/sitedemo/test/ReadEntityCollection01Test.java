package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.junit.jupiter.api.Test;

public class ReadEntityCollection01Test {
    private static final Log log = LogFactory.getLog(ReadEntityCollection01Test.class);

    @Test
    void test01() {
        ODataEntitySetRequest<ClientEntitySet> request = SitedemoTestUtil.getClient().getRetrieveRequestFactory()
                .getEntitySetRequest(SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.serviceUrl) //
                        .appendEntitySetSegment("ODataTests1") //
                        .count(true) //
                        .select("ID,Name") //
                        .filter("ID le 4") //
                        .orderBy("ID") //
                        .build());

        final ODataRetrieveResponse<ClientEntitySet> response = request.execute();
        final ClientEntitySet entitySet = response.getBody();
        log.info("    count: " + entitySet.getCount());
        for (ClientEntity entity : entitySet.getEntities()) {
            for (ClientProperty property : entity.getProperties()) {
                log.info("    " + property.getName() + ": " + property.getValue());
            }
        }
    }

}
