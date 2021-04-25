package jp.oiyokan.sitedemo.test;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.ODataClientErrorException;
import org.apache.olingo.client.api.communication.request.cud.ODataDeleteRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntityRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.response.ODataDeleteResponse;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
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

    public static void readEntityCollection(String entitySetName, boolean count, String select, String filter,
            String orderBy, Integer top, Integer skip) {
        ODataEntitySetRequest<ClientEntitySet> request = SitedemoTestUtil.getClient().getRetrieveRequestFactory()
                .getEntitySetRequest(SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl()) //
                        .appendEntitySetSegment(entitySetName) //
                        .count(count) //
                        .select(select) //
                        .filter(filter) //
                        .orderBy(orderBy) //
                        .top(top) //
                        .skip(skip) //
                        .build());
        request.setAccept("application/json;odata.metadata=full");

        final ODataRetrieveResponse<ClientEntitySet> response = request.execute();
        final ClientEntitySet entitySet = response.getBody();
        log.info("    count: " + entitySet.getCount());
        for (ClientEntity entity : entitySet.getEntities()) {
            log.info("  " + entitySetName);
            SitedemoTestUtil.printEntity(entity);
        }
    }

    ///////////////
    // Read common

    public static void printEntity(ClientEntity entity) {
        for (ClientProperty property : entity.getProperties()) {
            ClientPrimitiveValue priValue = property.getPrimitiveValue();
            log.info("    " + property.getName() + " (" + priValue.getType() + ")" + ": " + property.getValue());
        }
    }

    /////////////////
    // Delete
    public static boolean deleteEntryOne(String entitySetName, Object keySegment) {
        final URI uri = SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl())
                .appendEntitySetSegment(entitySetName).appendKeySegment(keySegment).build();
        final ODataDeleteRequest request = SitedemoTestUtil.getClient().getCUDRequestFactory().getDeleteRequest(uri);
        try {
            final ODataDeleteResponse response = request.execute();
            log.info("code:" + response.getStatusCode() + ": " + response.getStatusMessage());
            return true;
        } catch (ODataClientErrorException ex) {
            if (ex.getStatusLine().getStatusCode() == 404) {
                return false;
            } else {
                throw ex;
            }
        }
    }
}
