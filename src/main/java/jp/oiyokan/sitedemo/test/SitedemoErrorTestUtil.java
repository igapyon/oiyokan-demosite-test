package jp.oiyokan.sitedemo.test;

import java.net.URI;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.communication.ODataClientErrorException;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityUpdateRequest;
import org.apache.olingo.client.api.communication.request.cud.UpdateType;
import org.apache.olingo.client.api.communication.response.ODataEntityUpdateResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.commons.api.edm.FullQualifiedName;

public class SitedemoErrorTestUtil {
    private static final Log log = LogFactory.getLog(SitedemoErrorTestUtil.class);

    public static ClientEntity patchETag4Error(String entitySetName, Object keySegment, List<ClientProperty> properties,
            final boolean ifMatch, final boolean ifNoneMatch) {
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
        if (ifMatch) {
            request.setIfMatch("W/\"105201\"");
        }
        if (ifNoneMatch) {
            request.setIfNoneMatch("W/\"105202\"");
        }

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
}
