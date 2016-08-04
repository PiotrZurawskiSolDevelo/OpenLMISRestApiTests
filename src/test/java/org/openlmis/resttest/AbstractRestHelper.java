package org.openlmis.resttest;

public abstract class AbstractRestHelper {

    private String baseUrl;

    public AbstractRestHelper(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected String getBaseUrl() {
        return baseUrl;
    }
}
