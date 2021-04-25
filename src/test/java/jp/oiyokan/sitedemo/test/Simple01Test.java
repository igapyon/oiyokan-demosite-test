package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.apache.olingo.commons.api.format.ContentType;
import org.junit.jupiter.api.Test;

public class Simple01Test {
    private static final Log log = LogFactory.getLog(Simple01Test.class);

    public static final ODataClient client = ODataClientFactory.getClient();

    @Test
    void test01() {
        client.getConfiguration().setDefaultPubFormat(ContentType.APPLICATION_JSON);

        final Edm edm = client.getRetrieveRequestFactory()
                .getMetadataRequest("https://oiyokan-devel.herokuapp.com/odata4.svc/").execute().getBody();
        for (EdmEntitySet entitySet : edm.getEntityContainer().getEntitySets()) {
            log.info("EdmEntitySet: " + entitySet.getName());

            EdmEntityType entityType = entitySet.getEntityType();
            log.info("  Type: " + entityType.getName());

            for (String propertyName : entityType.getPropertyNames()) {
                EdmProperty property = entityType.getStructuralProperty(propertyName);

                log.info("      Property: " + property.getName() + " (" + property.getType() + ")");
                log.info("          " + property.isNullable());

            }

        }
    }

}
