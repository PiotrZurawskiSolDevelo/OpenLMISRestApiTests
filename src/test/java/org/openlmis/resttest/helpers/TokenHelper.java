package org.openlmis.resttest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TokenHelper {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    ObjectMapper mapper = new ObjectMapper();

    public String returnCreatedToken(String serverURL) throws IOException {
        String APIUrl = serverURL + "/oauth/token?grant_type=password&username=admin&password=password";
        builder.setContentType("application/json");
        RequestSpecification requestSpec = builder.build();
        Response response = given().authentication().preemptive().basic("trusted-client", "secret").spec(requestSpec).when().post(APIUrl);
        String responseSting = response.getBody().asString();
        JsonNode obj = mapper.readTree(responseSting);
        return obj.get("access_token").textValue();
    }
}