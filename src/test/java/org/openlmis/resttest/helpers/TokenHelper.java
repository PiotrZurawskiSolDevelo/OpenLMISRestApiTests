package org.openlmis.resttest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openlmis.resttest.AbstractRestHelper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;

public class TokenHelper extends AbstractRestHelper {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    ObjectMapper mapper = new ObjectMapper();

    public TokenHelper(String baseUrl) {
        super(baseUrl, "/oauth/token");
    }

    public String returnCreatedToken(String username, String password) throws IOException {
        try {
            URI apiUrl = uriBuilder()
                    .addParameter("grant_type", "password")
                    .addParameter("username", username)
                    .addParameter("password", password)
                    .build();

            builder.setContentType("application/json");
            RequestSpecification requestSpec = builder.build();

            Response response = given().auth().preemptive().basic("trusted-client", "secret").spec(requestSpec).when().post(apiUrl);

            String responseSting = response.getBody().asString();
            JsonNode obj = mapper.readTree(responseSting);

            return obj.get("access_token").textValue();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Unable to build url", e);
        }
    }
}
