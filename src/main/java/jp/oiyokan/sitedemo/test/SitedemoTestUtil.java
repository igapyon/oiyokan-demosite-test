package jp.oiyokan.sitedemo.test;

import java.net.URI;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.api.communication.ODataClientErrorException;
import org.apache.olingo.client.api.communication.request.cud.ODataDeleteRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityCreateRequest;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityUpdateRequest;
import org.apache.olingo.client.api.communication.request.cud.UpdateType;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntityRequest;
import org.apache.olingo.client.api.communication.request.retrieve.ODataEntitySetRequest;
import org.apache.olingo.client.api.communication.response.ODataDeleteResponse;
import org.apache.olingo.client.api.communication.response.ODataEntityCreateResponse;
import org.apache.olingo.client.api.communication.response.ODataEntityUpdateResponse;
import org.apache.olingo.client.api.communication.response.ODataRetrieveResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientEntitySet;
import org.apache.olingo.client.api.domain.ClientPrimitiveValue;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
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

    ////////////////
    // Create

    public static ClientEntity createEntityOne(String entitySetName, final List<ClientProperty> properties) {
        ClientEntity newEntity = SitedemoTestUtil.getClient().getObjectFactory()
                .newEntity(new FullQualifiedName("Container", entitySetName));
        for (ClientProperty prop : properties) {
            newEntity.getProperties().add(prop);
        }

        final ODataEntityCreateRequest<ClientEntity> createRequest = SitedemoTestUtil.getClient().getCUDRequestFactory()
                .getEntityCreateRequest(SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl())
                        .appendEntitySetSegment(entitySetName).build(), newEntity);
        try {
            final ODataEntityCreateResponse<ClientEntity> createResponse = createRequest.execute();
            if (201 != createResponse.getStatusCode()) {
                log.error("UNEXPECTED: 201以外の値が返却された: " + createResponse.getStatusCode() + ": "
                        + createResponse.getStatusMessage());
                return null;
            }
            final ClientEntity createdEntity = createResponse.getBody();

            log.info("  " + entitySetName);
            printEntity(createdEntity);

            return createdEntity;
        } catch (ODataClientErrorException ex) {
            if (ex.getStatusLine().getStatusCode() == 409) {
                return null;
            } else {
                throw ex;
            }
        }
    }

    /////////////////
    // Read util

    public static ClientEntity readEntityOne(String entitySetName, Object keySegment) {
        ODataEntityRequest<ClientEntity> request = SitedemoTestUtil.getClient().getRetrieveRequestFactory()
                .getEntityRequest(SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl()) //
                        .appendEntitySetSegment(entitySetName) //
                        .appendKeySegment(keySegment) //
                        .build());
        request.setAccept("application/json;odata.metadata=full");

        try {

            final ODataRetrieveResponse<ClientEntity> response = request.execute();

            if (200 != response.getStatusCode()) {
                log.error(
                        "UNEXPECTED: 200以外の値が返却された: " + response.getStatusCode() + ": " + response.getStatusMessage());
                return null;
            }

            final ClientEntity entity = response.getBody();
            log.info("  EntitySet: " + entitySetName);
            printEntity(entity);
            return entity;
        } catch (ODataClientErrorException ex) {
            if (404 == ex.getStatusLine().getStatusCode()) {
                return null;
            } else {
                throw ex;
            }
        }
    }

    public static ClientEntitySet readEntityCollection(String entitySetName, boolean count, String select,
            String filter, String orderBy, Integer top, Integer skip) {
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

        if (200 != response.getStatusCode()) {
            log.error("UNEXPECTED: 200以外の値が返却された: 結果0件であっても200を戻す: " + response.getStatusCode() + ": "
                    + response.getStatusMessage());
            return null;
        }

        final ClientEntitySet entitySet = response.getBody();
        log.info("    count: " + entitySet.getCount());
        for (ClientEntity entity : entitySet.getEntities()) {
            log.info("  " + entitySetName);
            SitedemoTestUtil.printEntity(entity);
        }

        return entitySet;
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

            if (204 != response.getStatusCode()) {
                log.error(
                        "UNEXPECTED: 204以外の値が返却された: " + response.getStatusCode() + ": " + response.getStatusMessage());
                return false;
            }

            log.info("code:" + response.getStatusCode() + ": " + response.getStatusMessage());
            return true;
        } catch (ODataClientErrorException ex) {
            if (404 == ex.getStatusLine().getStatusCode()) {
                return false;
            } else {
                throw ex;
            }
        }
    }

    //////////////////
    // PATCH

    //////////////////
    // PATCH - INSERT

    public static ClientEntity patchInsertEntryOne(String entitySetName, Object keySegment,
            List<ClientProperty> properties) {
        final URI uri = SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl()) //
                .appendEntitySetSegment(entitySetName) //
                .appendKeySegment(keySegment) //
                .build();

        final ClientEntity entity = SitedemoTestUtil.getClient().getObjectFactory()
                .newEntity(new FullQualifiedName("Container", entitySetName));
        for (ClientProperty prop : properties) {
            entity.getProperties().add(prop);
        }

        final ODataEntityUpdateRequest<ClientEntity> request = SitedemoTestUtil.getClient().getCUDRequestFactory()
                .getEntityUpdateRequest(uri, UpdateType.PATCH, entity);
        // INSERT限定
        request.setIfNoneMatch("*");
        try {
            final ODataEntityUpdateResponse<ClientEntity> response = request.execute();

            if (201 != response.getStatusCode()) {
                log.error(
                        "UNEXPECTED: 201以外の値が返却された: " + response.getStatusCode() + ": " + response.getStatusMessage());
                return null;
            }

            final ClientEntity entityInserted = response.getBody();
            log.info("  " + entitySetName);
            SitedemoTestUtil.printEntity(entityInserted);
            return entityInserted;
        } catch (ODataClientErrorException ex) {
            if (409 == ex.getStatusLine().getStatusCode()) {
                return null;
            } else {
                throw ex;
            }
        }
    }

    //////////////////
    // PATCH - UPDATE

    public static ClientEntity patchUpdateEntryOne(String entitySetName, Object keySegment,
            List<ClientProperty> properties) {
        final URI uri = SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl()) //
                .appendEntitySetSegment(entitySetName) //
                .appendKeySegment(keySegment) //
                .build();

        final ClientEntity entity = SitedemoTestUtil.getClient().getObjectFactory()
                .newEntity(new FullQualifiedName("Container", entitySetName));
        for (ClientProperty prop : properties) {
            entity.getProperties().add(prop);
        }

        final ODataEntityUpdateRequest<ClientEntity> request = SitedemoTestUtil.getClient().getCUDRequestFactory()
                .getEntityUpdateRequest(uri, UpdateType.PATCH, entity);
        // UPDATE限定
        request.setIfMatch("*");
        try {
            final ODataEntityUpdateResponse<ClientEntity> response = request.execute();
            if (200 != response.getStatusCode()) {
                log.error(
                        "UNEXPECTED: 200以外の値が返却された: " + response.getStatusCode() + ": " + response.getStatusMessage());
                return null;
            }

            final ClientEntity entityUpdated = response.getBody();
            log.info("  " + entitySetName);
            SitedemoTestUtil.printEntity(entityUpdated);
            return entityUpdated;
        } catch (ODataClientErrorException ex) {
            if (404 == ex.getStatusLine().getStatusCode()) {
                return null;
            } else {
                throw ex;
            }
        }
    }

    //////////////////
    // PATCH - UPSERT

    public static ClientEntity patchUpsertEntryOne(String entitySetName, Object keySegment,
            List<ClientProperty> properties) {
        final URI uri = SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl()) //
                .appendEntitySetSegment(entitySetName) //
                .appendKeySegment(keySegment) //
                .build();

        final ClientEntity entity = SitedemoTestUtil.getClient().getObjectFactory()
                .newEntity(new FullQualifiedName("Container", entitySetName));
        for (ClientProperty prop : properties) {
            entity.getProperties().add(prop);
        }

        final ODataEntityUpdateRequest<ClientEntity> request = SitedemoTestUtil.getClient().getCUDRequestFactory()
                .getEntityUpdateRequest(uri, UpdateType.PATCH, entity);
        // UPSERT (UPDATE or INSERT)
        try {
            final ODataEntityUpdateResponse<ClientEntity> response = request.execute();
            if (200 != response.getStatusCode() //
                    && 201 != response.getStatusCode()) {
                log.error("UNEXPECTED: 200でも201でもない値が返却された: " + response.getStatusCode() + ": "
                        + response.getStatusMessage());
                return null;
            }

            final ClientEntity entityUpserted = response.getBody();
            log.info("  " + entitySetName);
            SitedemoTestUtil.printEntity(entityUpserted);
            return entityUpserted;
        } catch (ODataClientErrorException ex) {
            throw ex;
        }
    }

    public static ClientProperty newPropertyString(String name, String value) {
        return getClient().getObjectFactory().newPrimitiveProperty(name,
                getClient().getObjectFactory().newPrimitiveValueBuilder().buildString(value));
    }

    public static ClientProperty newPropertyInt32(String name, Integer value) {
        return getClient().getObjectFactory().newPrimitiveProperty(name,
                getClient().getObjectFactory().newPrimitiveValueBuilder().buildInt32(value));
    }
}
