package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.domain.ClientPrimitiveValue;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class ReadEntityCollection02Test {
    private static final Log log = LogFactory.getLog(ReadEntityCollection02Test.class);

    @Test
    void test01() {
        ODataEntitySetRequest<ClientEntitySet> request = SitedemoTestUtil.getClient().getRetrieveRequestFactory()
                .getEntitySetRequest(SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.serviceUrl) //
                        .appendEntitySetSegment("ODataTests1") //
                        .count(true) //
                        .select("ID,Name,DateTimeOffset1") //
                        .filter("ID ge 200") //
                        .orderBy("ID desc") //
                        .build());
        request.setAccept("application/json;odata.metadata=full");

        final ODataRetrieveResponse<ClientEntitySet> response = request.execute();
        final ClientEntitySet entitySet = response.getBody();
        log.info("    count: " + entitySet.getCount());
        for (ClientEntity entity : entitySet.getEntities()) {
            log.info("  " + "ODataTest1");
            for (ClientProperty property : entity.getProperties()) {
                ClientPrimitiveValue priValue = property.getPrimitiveValue();
                log.info("    " + property.getName() + " (" + priValue.getType() + ")" + ": " + property.getValue());
            }
        }
    }

}
