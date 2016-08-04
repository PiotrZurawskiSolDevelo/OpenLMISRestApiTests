package org.openlmis.resttest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openlmis.resttest.AbstractRestHelper;

import java.io.IOException;
import java.net.URI;

import static io.restassured.RestAssured.given;

public class ProgramHelper extends AbstractRestHelper {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    ObjectMapper mapper = new ObjectMapper();

    public ProgramHelper(String baseUrl) {
        super(baseUrl, "/api/programs");
    }

    public JsonNode createOrEditProgramUsingAllVariables(String token, String jsonBody) throws IOException {
        URI apiUrl = uri(token);

        builder.setContentType("application/json");
        builder.setBody(jsonBody);

        RequestSpecification requestSpec = builder.build();

        Response response = given().spec(requestSpec).post(apiUrl);
        String responseSting = response.asString();

        return mapper.readTree(responseSting);
    }
}
