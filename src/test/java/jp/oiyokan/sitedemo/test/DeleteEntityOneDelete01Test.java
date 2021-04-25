package jp.oiyokan.sitedemo.test;

import java.net.URI;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.olingo.client.api.communication.request.cud.ODataDeleteRequest;
import org.apache.olingo.client.api.communication.response.ODataDeleteResponse;
import org.junit.jupiter.api.Test;

public class DeleteEntityOneDelete01Test {
    private static final Log log = LogFactory.getLog(DeleteEntityOneDelete01Test.class);

    @Test
    void test01() {
        final URI uri = SitedemoTestUtil.getClient().newURIBuilder(SitedemoTestUtil.getServiceUrl())
                .appendEntitySetSegment("ODataTests1").appendKeySegment(215).build();
        final ODataDeleteRequest request = SitedemoTestUtil.getClient().getCUDRequestFactory().getDeleteRequest(uri);
        final ODataDeleteResponse response = request.execute();
        log.info("code:" + response.getStatusCode() + ": " + response.getStatusMessage());
    }
}
