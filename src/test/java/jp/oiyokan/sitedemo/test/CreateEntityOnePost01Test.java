package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.communication.request.cud.ODataEntityCreateRequest;
import org.apache.olingo.client.api.communication.response.ODataEntityCreateResponse;
import org.apache.olingo.client.api.domain.ClientEntity;
import org.apache.olingo.client.api.domain.ClientPrimitiveValue;
import org.apache.olingo.client.api.domain.ClientProperty;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.junit.jupiter.api.Test;

public class CreateEntityOnePost01Test {
    private static final Log log = LogFactory.getLog(CreateEntityOnePost01Test.class);

    @Test
    void test01() {
        ClientEntity newEntity = SitedemoTestUtil.getClient().getObjectFactory()
                .newEntity(new FullQualifiedName("Container", "ODataTests1"));
        newEntity.getProperties()
                .add(SitedemoTestUtil.getClient().getObjectFactory().newPrimitiveProperty("Name", SitedemoTestUtil
                        .getClient().getObjectFactory().newPrimitiveValueBuilder().buildString("Create Entity Test.")));

        final ODataEntityCreateRequest<ClientEntity> createRequest = SitedemoTestUtil.getClient().getCUDRequestFactory()
                .getEntityCreateRequest(SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl())
                        .appendEntitySetSegment("ODataTests1").build(), newEntity);
        final ODataEntityCreateResponse<ClientEntity> createResponse = createRequest.execute();
        final ClientEntity createdEntity = createResponse.getBody();

        for (ClientProperty property : createdEntity.getProperties()) {
            ClientPrimitiveValue priValue = property.getPrimitiveValue();
            log.info("    " + property.getName() + " (" + priValue.getType() + ")" + ": " + property.getValue());
        }
    }
}
