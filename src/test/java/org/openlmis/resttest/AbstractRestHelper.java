package org.openlmis.resttest;

import org.apache.http.client.utils.URIBuilder;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class AbstractRestHelper {

    private String baseUrl;
    private String path;

    public AbstractRestHelper(String baseUrl, String path) {
        this.baseUrl = baseUrl;
        this.path = path;
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    protected String getPath() {
        return path;
    }

    protected URIBuilder uriBuilder() {
        return uriBuilder(null);
    }

    protected URIBuilder uriBuilder(String token) {
        try {
            URIBuilder uriBuilder = new URIBuilder(getBaseUrl());
            uriBuilder.setPath(path);

            if (token != null) {
                uriBuilder.addParameter("access_token", token);
            }

            return uriBuilder;
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to build url", e);
        }
    }

    protected URI uri() {
        return uri(null);
    }

    protected URI uri(String token) {
        try {
            return uriBuilder(token).build();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to build url", e);
        }
    }
}
