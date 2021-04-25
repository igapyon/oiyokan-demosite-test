package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmKeyPropertyRef;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.junit.jupiter.api.Test;

public class ReadEdmEntitySet01Test {
    private static final Log log = LogFactory.getLog(ReadEdmEntitySet01Test.class);

    public static final ODataClient client = ODataClientFactory.getClient();

    public static final String serviceUrl = "https://oiyokan-devel.herokuapp.com/odata4.svc/";

    @Test
    void test01() {
        client.getConfiguration().setDefaultPubFormat(ContentType.APPLICATION_JSON);

        final Edm edm = client.getRetrieveRequestFactory().getMetadataRequest(serviceUrl).execute().getBody();
        for (EdmEntitySet entitySet : edm.getEntityContainer().getEntitySets()) {
            log.info("EdmEntitySet: " + entitySet.getName());

            EdmEntityType entityType = entitySet.getEntityType();
            log.info("  Type: " + entityType.getName());

            for (EdmKeyPropertyRef propertyRef : entityType.getKeyPropertyRefs()) {
                log.info("      Key: " + propertyRef.getName());
            }

            for (String propertyName : entityType.getPropertyNames()) {
                EdmProperty property = entityType.getStructuralProperty(propertyName);

                log.info("      Property: " + property.getName() + " (" + property.getType() + ")");
                log.info("          " + property.isNullable());

            }

        }
    }
}
