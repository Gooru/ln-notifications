package org.gooru.notifications.responses.transformers;

import io.vertx.core.json.JsonObject;
import org.gooru.notifications.infra.exceptions.HttpResponseWrapperException;

import java.util.Collections;
import java.util.Map;

/**
 * @author ashish.
 */
public final class HttpResponseWrapperExceptionTransformer implements ResponseTransformer {

    private final HttpResponseWrapperException ex;

    HttpResponseWrapperExceptionTransformer(HttpResponseWrapperException ex) {
        this.ex = ex;
    }

    @Override
    public void transform() {
        // no op
    }

    @Override
    public JsonObject transformedBody() {
        return ex.getBody();
    }

    @Override
    public Map<String, String> transformedHeaders() {
        return Collections.emptyMap();
    }

    @Override
    public int transformedStatus() {
        return ex.getStatus();
    }
}
