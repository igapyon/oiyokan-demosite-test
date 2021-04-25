package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntityRequest;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientPrimitiveValue;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.format.ContentType;

public class SitedemoTestUtil {
    private static final Log log = LogFactory.getLog(SitedemoTestUtil.class);

    private static volatile Edm edm = null;
    private static volatile ODataClient client = null;

    public static String getServiceUrl() {
        return SitedemoTestConstants.SERVICE_URL;
    }

    public static final ODataClient getClient() {
        if (client == null) {
            log.info("Start client instance:" + getServiceUrl());
            client = ODataClientFactory.getClient();
        }
        client.getConfiguration().setDefaultPubFormat(ContentType.APPLICATION_JSON);

        return client;
    }

    public static Edm getEdm() {
        if (edm == null) {
            log.info("Start retrieve EDM from site:" + getServiceUrl());
            edm = SitedemoTestUtil.getClient().getRetrieveRequestFactory()
                    .getMetadataRequest(SitedemoTestUtil.getServiceUrl()).execute().getBody();
        }

        return edm;
    }

    /////////////////
    // Read util

    public static void readEntityOne(String entitySetName, Object keySegment) {
        ODataEntityRequest<ClientEntity> request = SitedemoTestUtil.getClient().getRetrieveRequestFactory()
                .getEntityRequest(SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl()) //
                        .appendEntitySetSegment(entitySetName) //
                        .appendKeySegment(keySegment) //
                        .build());
        request.setAccept("application/json;odata.metadata=full");

        final ODataRetrieveResponse<ClientEntity> response = request.execute();
        final ClientEntity entity = response.getBody();
        log.info("  EntitySet: " + entitySetName);
        printEntity(entity);
    }

    public static void printEntity(ClientEntity entity) {
        for (ClientProperty property : entity.getProperties()) {
            ClientPrimitiveValue priValue = property.getPrimitiveValue();
            log.info("    " + property.getName() + " (" + priValue.getType() + ")" + ": " + property.getValue());
        }
    }

}
