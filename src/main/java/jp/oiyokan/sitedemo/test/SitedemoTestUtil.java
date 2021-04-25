package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.format.ContentType;

public class SitedemoTestUtil {
    private static final Log log = LogFactory.getLog(SitedemoTestUtil.class);

    private static volatile Edm edm = null;
    private static volatile ODataClient client = null;

    public static String getServiceUrl() {
        return SitedemoTestConstants.serviceUrl;
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
}
