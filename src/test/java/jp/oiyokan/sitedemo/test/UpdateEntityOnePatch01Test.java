package jp.oiyokan.sitedemo.test;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityUpdateRequest;
import org.apache.olingo.client.api.communication.request.cud.UpdateType;
import org.apache.olingo.client.api.communication.response.ODataEntityUpdateResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.junit.jupiter.api.Test;

public class UpdateEntityOnePatch01Test {
    private static final Log log = LogFactory.getLog(UpdateEntityOnePatch01Test.class);

    @Test
    void test01() {
        final URI uri = SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.serviceUrl) //
                .appendEntitySetSegment("ODataTests1") //
                .appendKeySegment(205) //
                .build();

        final ClientEntity entity = SitedemoTestUtil.getClient().getObjectFactory()
                .newEntity(new FullQualifiedName("Container", "ODataTests1"));
        entity.getProperties()
                .add(SitedemoTestUtil.getClient().getObjectFactory().newPrimitiveProperty("Name", SitedemoTestUtil
                        .getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("Updated value2")));

        final ODataEntityUpdateRequest<ClientEntity> requestUpdate = SitedemoTestUtil.getClient().getCUDRequestFactory()
                .getEntityUpdateRequest(uri, UpdateType.PATCH, entity);
        // UPDATE限定
        requestUpdate.getIfMatch();
        final ODataEntityUpdateResponse<ClientEntity> responseUpdate = requestUpdate.execute();

        if (responseUpdate.getStatusCode() == 204) {
            // nothing.
        } else {
            final ClientEntity entity2 = responseUpdate.getBody();
            for (ClientProperty property : entity2.getProperties()) {
                log.info("    " + property.getName() + ": " + property.getValue());
            }
        }
    }
}
