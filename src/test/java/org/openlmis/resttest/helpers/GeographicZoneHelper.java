package org.openlmis.resttest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openlmis.resttest.AbstractRestHelper;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GeographicZoneHelper extends AbstractRestHelper {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    ObjectMapper mapper = new ObjectMapper();

    public GeographicZoneHelper(String url) {
        super(url);
    }

    public JsonNode createGeographicZones(String token, String jsonBody) throws IOException {
        String APIUrl = getBaseUrl() + "/api/geographicZones" + token;
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        RequestSpecification requestSpec = builder.build();
        Response response = given().spec(requestSpec).post(APIUrl);
        String responseSting = response.asString();
        return mapper.readTree(responseSting);
    }
}
