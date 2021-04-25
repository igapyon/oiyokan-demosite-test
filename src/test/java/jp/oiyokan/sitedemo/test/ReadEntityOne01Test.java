package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntityRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientPrimitiveValue;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.junit.jupiter.api.Test;

public class ReadEntityOne01Test {
    private static final Log log = LogFactory.getLog(ReadEntityOne01Test.class);

    @Test
    void test01() {
        ODataEntityRequest<ClientEntity> request = SitedemoTestUtil.getClient().getRetrieveRequestFactory()
                .getEntityRequest(SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.serviceUrl) //
                        .appendEntitySetSegment("ODataTests1") //
                        .appendKeySegment(1) //
                        .build());
        request.setAccept("application/json;odata.metadata=full");

        final ODataRetrieveResponse<ClientEntity> response = request.execute();
        final ClientEntity entity = response.getBody();

        for (ClientProperty property : entity.getProperties()) {
            ClientPrimitiveValue priValue = property.getPrimitiveValue();
            log.info("    " + property.getName() + " (" + priValue.getType() + ")" + ": " + property.getValue());
        }
    }

}
