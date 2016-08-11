package org.openlmis.resttest.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openlmis.resttest.AbstractRestHelper;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Calendar;

import static io.restassured.RestAssured.given;

public class PeriodHelper extends AbstractRestHelper {

    public PeriodHelper(String baseUrl) { super(baseUrl, "/api/periods"); }
    /**
     * This method should create or modify period.
     */
    public JsonNode createPeriod(String token, String jsonBody) throws IOException {
        URI apiUrl = uri(token);

        RequestSpecBuilder builder = getRequestSpecBuilder();

        builder.setContentType("application/json");
        builder.setBody(jsonBody);

        RequestSpecification requestSpec = builder.build();

        Response response = given().spec(requestSpec).post(apiUrl);
        String responseSting = response.asString();
        System.out.println(responseSting);
        return getObjectMapper().readTree(responseSting);
    }
    /**
     * This method should return all Periods in one JSON.
     */
    public JsonNode getPeriods(String token) throws IOException {
        URI apiUrl = uri(token);

        RequestSpecBuilder builder = getRequestSpecBuilder();

        builder.setContentType("application/json");

        RequestSpecification requestSpec = builder.build();

        Response response = given().spec(requestSpec).get(apiUrl);
        String responseSting = response.asString();
        System.out.println(responseSting);
        return getObjectMapper().readTree(responseSting);
    }
    /**
     * This method should return the count of the Periods elements.
     */
    public Integer returnCountOfTheCreatedPeriods(JsonNode periods) throws IOException {
        return periods.get("page").get("totalElements").asInt();
    }
    /**
     * This method should return the latest day used in the periods.
     */
    public String findTheLatesEndDate(String token) throws ParseException, IOException {
        JsonNode periods = getPeriods(token);
        Integer periodsCount = returnCountOfTheCreatedPeriods(periods);
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        String latesDate = "";
        ArrayNode arrayNode = (ArrayNode) periods.get("_embedded").get("periods");
        String dateFromPeriod;
        for ( int i = 0 ; i < periodsCount; i++) {
            dateFromPeriod = arrayNode.get(i).get("endDate").asText();
            calendar1.setTime(getSimpleDateFormat().parse(dateFromPeriod));
            if (latesDate.equals("")) {
                latesDate = getSimpleDateFormat().format(calendar1.getTime());
            } else {
                calendar2.setTime(getSimpleDateFormat().parse(latesDate));
                if(calendar1.after(calendar2)) {
                    latesDate = getSimpleDateFormat().format(calendar1.getTime());
                }
            }
        }
        return latesDate;
    }
}
