package org.openlmis.resttest.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openlmis.resttest.AbstractRestTest;
import org.openlmis.resttest.util.JsonUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class DefinePeriodsWithinAScheduleRestTest extends AbstractRestTest {

    private String token;
    static final String START_DATE_WARNING = "Start date should be one day after last added end date";

    @Before
    public void setUp() throws IOException {
        token = getTokenHelper().returnCreatedToken(getUsername(), getPassword());
    }

    // TODO: map logic should go to helpers
    //OLMIS-755
    @Test
    public void definePeriodsWithinASchedule() throws IOException, ParseException {
        String firstStartDate;
        /**Creating schedule*/
        Map<String, String> valuesMap = new HashMap<>();
        String code = RandomStringUtils.randomAlphabetic(5);
        String name = RandomStringUtils.randomAlphabetic(5);
        String description = RandomStringUtils.randomAlphabetic(10);
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("description", description);
        String convertedJson = JsonUtil.readJsonFileAsString("json/Schedule.json", valuesMap);
        JsonNode schedule = getScheduleHelper().createSchedule(token, convertedJson);
        /**Creating first period*/
        valuesMap.clear();
        String latestDayFromPeriod = getPeriodHelper().findTheLatesEndDate(token);
        code = schedule.get("code").asText();
        description = schedule.get("description").asText();
        name = schedule.get("name").asText();
        String modifiedDate = schedule.get("modifiedDate").asText();
        String id = schedule.get("_links").get("schedule").get("href").asText().substring((getRequisitionsUrl() + "/api/schedules/").length());
        if (latestDayFromPeriod.equals("")) {
            firstStartDate = genrateDate();
        } else {
            firstStartDate = generateDateAfterGivenDate(latestDayFromPeriod, 1);
        }
        String firstEndDate = generateDateAfterGivenDate(firstStartDate, 2);
        valuesMap.put("id", id);
        valuesMap.put("code", code);
        valuesMap.put("scheduleDescription", description);
        valuesMap.put("scheduleName", name);
        valuesMap.put("modifiedDate", modifiedDate);
        valuesMap.put("name", RandomStringUtils.randomAlphabetic(5));
        valuesMap.put("description", RandomStringUtils.randomAlphabetic(10));
        valuesMap.put("startDate", firstStartDate);
        valuesMap.put("endDate", firstEndDate);
        convertedJson = JsonUtil.readJsonFileAsString("json/Period.json", valuesMap);
        JsonNode firstPeriod = getPeriodHelper().createPeriod(token, convertedJson);
        /**Creating period with the from start date*/
        valuesMap.clear();
        code = schedule.get("code").asText();
        description = schedule.get("description").asText();
        name = schedule.get("name").asText();
        modifiedDate = schedule.get("modifiedDate").asText();
        id = schedule.get("_links").get("schedule").get("href").asText().substring((getRequisitionsUrl() + "/api/schedules/").length());
        String secondStartDate = generateDateAfterGivenDate(firstEndDate, 5);
        String secondEndDate = generateDateAfterGivenDate(secondStartDate, 5);
        valuesMap.put("id", id);
        valuesMap.put("code", code);
        valuesMap.put("scheduleDescription", description);
        valuesMap.put("scheduleName", name);
        valuesMap.put("modifiedDate", modifiedDate);
        valuesMap.put("name", RandomStringUtils.randomAlphabetic(5));
        valuesMap.put("description", RandomStringUtils.randomAlphabetic(10));
        valuesMap.put("startDate", secondStartDate);
        valuesMap.put("endDate", secondEndDate);
        convertedJson = JsonUtil.readJsonFileAsString("json/Period.json", valuesMap);
        JsonNode secondPeriod = getPeriodHelper().createPeriod(token, convertedJson);
        /**Asserting response from the server*/
        Assert.assertEquals(START_DATE_WARNING, secondPeriod.get("startDate").asText());
        /**Creating second period with correct start date*/
        valuesMap.clear();
        latestDayFromPeriod = getPeriodHelper().findTheLatesEndDate(token);
        code = schedule.get("code").asText();
        description = schedule.get("description").asText();
        name = schedule.get("name").asText();
        modifiedDate = schedule.get("modifiedDate").asText();
        id = schedule.get("_links").get("schedule").get("href").asText().substring((getRequisitionsUrl() + "/api/schedules/").length());
        String thirdStartDate = generateDateAfterGivenDate(latestDayFromPeriod, 1);
        String thirdEndDate = generateDateAfterGivenDate(secondStartDate, 2);
        valuesMap.put("id", id);
        valuesMap.put("code", code);
        valuesMap.put("scheduleDescription", description);
        valuesMap.put("scheduleName", name);
        valuesMap.put("modifiedDate", modifiedDate);
        valuesMap.put("name", RandomStringUtils.randomAlphabetic(5));
        valuesMap.put("description", RandomStringUtils.randomAlphabetic(10));
        valuesMap.put("startDate", thirdStartDate);
        valuesMap.put("endDate", thirdEndDate);
        convertedJson = JsonUtil.readJsonFileAsString("json/Period.json", valuesMap);
        JsonNode thirdPeriod = getPeriodHelper().createPeriod(token, convertedJson);
        String calculatedDifference = getPeriodHelper().getCalculatedDaysAndMonths(token, firstPeriod.get("id").asText());
        /**Asserting response from the server*/
        Assert.assertNotEquals(firstPeriod.get("id").asText(), thirdPeriod.get("id").asText());
        Assert.assertEquals(firstPeriod.get("processingSchedule").get("id").asText(), thirdPeriod.get("processingSchedule").get("id").asText());
        Assert.assertEquals(calculatedDifference, "\"Period lasts 0 months and " + calculateDifferenceBetweenDates(firstStartDate, firstEndDate) + " days\"");
    }
}
