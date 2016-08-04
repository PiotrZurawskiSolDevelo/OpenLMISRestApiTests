package org.openlmis.resttest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.openlmis.resttest.AbstractRestHelper;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class ScheduleHelper extends AbstractRestHelper {

    RequestSpecBuilder builder = new RequestSpecBuilder();
    ObjectMapper mapper = new ObjectMapper();

    public ScheduleHelper(String baseUrl) {
        super(baseUrl);
    }

    public JsonNode createSchedule(String token) throws IOException {
        String APIUrl = getBaseUrl() + "/api/schedules" + token;
        String APIBody = "{\"code\":\"" + RandomStringUtils.randomAlphabetic(5) + "\"," +
                "\"name\":\"" + RandomStringUtils.randomAlphabetic(5) + "\"}";
        builder.setContentType("application/json");
        builder.setBody(APIBody);
        RequestSpecification requestSpec = builder.build();
        Response response = given().spec(requestSpec).post(APIUrl);
        String responseSting = response.asString();
        return mapper.readTree(responseSting);
    }
}
