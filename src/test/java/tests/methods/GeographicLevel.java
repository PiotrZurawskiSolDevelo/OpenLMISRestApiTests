package tests.methods;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Random;

import static io.restassured.RestAssured.given;

/**
 * Created by user on 8/2/16.
 */
public class GeographicLevel {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    Random rand = new Random();
    ObjectMapper mapper = new ObjectMapper();

    public JsonNode createGeographicLevel(String serverURL, Integer portNumber, String token, String jsonBody) throws IOException {
        String APIUrl = serverURL + portNumber + "/api/geographicLevels" + token;
        builder.setContentType("application/json");
        builder.setBody(jsonBody);
        RequestSpecification requestSpec = builder.build();
        Response response = given().spec(requestSpec).post(APIUrl);
        String responseSting = response.asString();
        return mapper.readTree(responseSting);
    }
}
