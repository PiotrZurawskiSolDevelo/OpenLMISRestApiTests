package org.openlmis.resttest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class FacilityHelper {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    ObjectMapper mapper = new ObjectMapper();

    public JsonNode createFacility(String serverURL, Integer portNumber ,String token, String facilityTypesHref, String geographicZonesHref) throws IOException {
        String APIUrl = serverURL + portNumber + "/api/facilities" + token;
        String APIBody = "{\"code\":\"" + RandomStringUtils.randomAlphabetic(5) + "\"," +
                "\"geographicZone\":\"" + geographicZonesHref + "\"," +
                "\"type\":\"" + facilityTypesHref + "\"," +
                "\"active\":" + true + "," +
                "\"enabled\": " + false + "}";
        builder.setContentType("application/json");
        builder.setBody(APIBody);
        RequestSpecification requestSpec = builder.build();
        Response response = given().spec(requestSpec).post(APIUrl);
        String responseSting = response.asString();
        return mapper.readTree(responseSting);
    }
}
