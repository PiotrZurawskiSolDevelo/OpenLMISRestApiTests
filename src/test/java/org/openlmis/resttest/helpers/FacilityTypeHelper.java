package org.openlmis.resttest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class FacilityTypeHelper {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    ObjectMapper mapper = new ObjectMapper();

    public JsonNode createFacilityType(String serverURL, Integer portNumber, String token, String jsonBody) throws IOException {
        String APIUrl = serverURL + portNumber + "/api/facilityTypes" + token;
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        RequestSpecification requestSpec = builder.build();
        Response response = given().spec(requestSpec).post(APIUrl);
        String responseSting = response.asString();
        return mapper.readTree(responseSting);




    }
}

/**        JSONObject jsonObject = new JSONObject(response.getBody().asString());
 JSONObject linksObject = (JSONObject)jsonObject.get("_links");
 JSONObject facilityTypeObject = (JSONObject)linksObject.get("facilityType");
 return facilityTypeObject.getString("href");*/