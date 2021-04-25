package jp.oiyokan.sitedemo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.ODataClient;
import org.apache.olingo.client.core.ODataClientFactory;
import org.apache.olingo.commons.api.edm.Edm;
import org.apache.olingo.commons.api.format.ContentType;

public class SitedemoTestUtil {
    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(SitedemoTestUtil.class);

    public static final ODataClient client = ODataClientFactory.getClient();

    public static final String serviceUrl = "https://oiyokan-devel.herokuapp.com/odata4.svc/";

    public static final ODataClient getClient() {
        client.getConfiguration().setDefaultPubFormat(ContentType.APPLICATION_JSON);
        return client;
    }

    private static volatile Edm edm = null;

    public static Edm getEdm() {
        if (edm == null) {
            log.info("Start to retrieve EDM from site:" + serviceUrl);
            edm = SitedemoTestUtil.getClient().getRetrieveRequestFactory()
                    .getMetadataRequest(SitedemoTestUtil.serviceUrl).execute().getBody();
        }
        return edm;
    }
}
