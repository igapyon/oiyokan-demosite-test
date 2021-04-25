package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmKeyPropertyRef;
import org.apache.olingo.commons.api.edm.EdmProperty;
import org.junit.jupiter.api.Test;

public class ReadEdmAll01Test {
    private static final Log log = LogFactory.getLog(ReadEdmAll01Test.class);

    @Test
    void test01() {
        final Edm edm = SitedemoTestUtil.getEdm();
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
