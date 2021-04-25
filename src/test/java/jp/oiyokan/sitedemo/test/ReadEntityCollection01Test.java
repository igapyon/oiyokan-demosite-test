package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.format.ContentType;
import org.junit.jupiter.api.Test;

public class ReadEntityCollection01Test {
    private static final Log log = LogFactory.getLog(ReadEntityCollection01Test.class);

    public static final ODataClient client = ODataClientFactory.getClient();

    public static final String serviceUrl = "https://oiyokan-devel.herokuapp.com/odata4.svc/";

    @Test
    void test01() {
        client.getConfiguration().setDefaultPubFormat(ContentType.APPLICATION_JSON);

        ODataEntitySetRequest<ClientEntitySet> request = client.getRetrieveRequestFactory()
                .getEntitySetRequest(client.newURIBuilder(serviceUrl) //
                        .appendEntitySetSegment("ODataTests1") //
                        .count(true) //
                        .select("ID") //
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
