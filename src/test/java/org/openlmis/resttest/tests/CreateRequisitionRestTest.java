package org.openlmis.resttest.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openlmis.resttest.AbstractRestTest;
import org.openlmis.resttest.util.JsonUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateRequisitionRestTest extends AbstractRestTest {

    private String token;

    private Random rand = new Random();

    @Before
    public void setUp() throws IOException {
        token = getTokenHelper().returnCreatedToken(getUsername(), getPassword());
    }

    // TODO: map logic should go to helpers
    @Test
    public void createRequisition() throws IOException, ParseException {
        String code = RandomStringUtils.randomAlphabetic(5);
        String name = RandomStringUtils.randomAlphabetic(5);
        String description = RandomStringUtils.randomAlphabetic(10);
        String active = "false";
        String periodsSkippable = "false";
        String showNonFullSupplyTab = "false";
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("description", description);
        valuesMap.put("active", active);
        valuesMap.put("periodsSkippable", periodsSkippable);
        valuesMap.put("showNonFullSupplyTab", showNonFullSupplyTab);
        String convertedJson = JsonUtil.readJsonFileAsString("json/Program.json", valuesMap);
        JsonNode program = getProgramHelper().createOrEditProgram(token, convertedJson);

        valuesMap.clear();
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        Integer levelNumber = rand.nextInt(50);
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("levelNumber", levelNumber.toString());
        convertedJson = JsonUtil.readJsonFileAsString("json/GeographicLevel.json", valuesMap);
        JsonNode geographicLevelJson = getGeographicLevelHelper().createGeographicLevel(token, convertedJson);
        String level = geographicLevelJson.get("_links").get("geographicLevel").get("href").asText();

        valuesMap.clear();
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        Integer catchmentPopulation = rand.nextInt(50);
        Double latitude = rand.nextDouble() + 10;
        Double longitude = rand.nextDouble() + 10;
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("level", level);
        valuesMap.put("catchmentPopulation", catchmentPopulation.toString());
        valuesMap.put("latitude", latitude.toString());
        valuesMap.put("longitude", longitude.toString());
        convertedJson = JsonUtil.readJsonFileAsString("json/GeographicZone.json", valuesMap);
        JsonNode geographicZoneJson = getGeographicZoneHelper().createGeographicZones(token, convertedJson);
        String geographicZone = geographicZoneJson.get("_links").get("geographicZone").get("href").asText();

        valuesMap.clear();
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        description = RandomStringUtils.randomAlphabetic(10);
        Integer displayOrder = rand.nextInt(50);
        active = "false";
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("description", description);
        valuesMap.put("displayOrder", displayOrder.toString());
        valuesMap.put("active", active);
        convertedJson = JsonUtil.readJsonFileAsString("json/FacilityType.json", valuesMap);
        JsonNode facilityTypeJson = getFacilityTypeHelper().createFacilityType(token, convertedJson);
        String type = facilityTypeJson.get("_links").get("facilityType").get("href").asText();
/**
        valuesMap.clear();
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        description = RandomStringUtils.randomAlphabetic(10);
        displayOrder = rand.nextInt(50);
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("description", description);
        valuesMap.put("displayOrder", displayOrder.toString());
        convertedJson = JsonUtil.readJsonFileAsString("json/FacilityOperator.json", valuesMap);
        JsonNode facilityOperatorJson = getFacilityOperatorHelper().createFacilityOperator(token, convertedJson);
        String operator = facilityOperatorJson.get("_links").get("facilityOperator").get("href").asText();*/

        valuesMap.clear();
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        description = RandomStringUtils.randomAlphabetic(10);
        active = "false";
        String goLiveDate = genrateDate();
        String goDownDate = generateDateAfterGivenDate(goLiveDate, 1);
        String comment = RandomStringUtils.randomAlphabetic(10);
        String enabled = "false";
        String openLmisAccessible = "true";
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("description", description);
        valuesMap.put("geographicZone", geographicZone);
/**        valuesMap.put("operator", operator);*/
        valuesMap.put("type", type);
        valuesMap.put("active", active);
        valuesMap.put("goLiveDate", goLiveDate);
        valuesMap.put("goDownDate", goDownDate);
        valuesMap.put("comment", comment);
        valuesMap.put("enabled", enabled);
        valuesMap.put("openLmisAccessible", openLmisAccessible);
        convertedJson = JsonUtil.readJsonFileAsString("json/Facility.json", valuesMap);
        JsonNode facilityJson = getFacilityHelper().createFacility(token, convertedJson);
        String facility = facilityJson.get("_links").get("facility").get("href").asText();

        valuesMap.clear();
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        description = RandomStringUtils.randomAlphabetic(10);
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("description", description);
        convertedJson = JsonUtil.readJsonFileAsString("json/Schedule.json", valuesMap);
        JsonNode schedule = getScheduleHelper().createSchedule(token, convertedJson);

        valuesMap.clear();
        code = schedule.get("code").asText();
        description = schedule.get("description").asText();
        name = schedule.get("name").asText();
        String modifiedDate = schedule.get("modifiedDate").asText();
        String id = schedule.get("_links").get("schedule").get("href").asText().substring((getRequisitionsUrl() + "/api/schedules/").length());
        valuesMap.put("id", id);
        valuesMap.put("code", code);
        valuesMap.put("scheduleDescription", description);
        valuesMap.put("scheduleName", name);
        valuesMap.put("modifiedDate", modifiedDate);
        valuesMap.put("name", RandomStringUtils.randomAlphabetic(5));
        valuesMap.put("description", RandomStringUtils.randomAlphabetic(10));
        String date = genrateDate();
        valuesMap.put("startDate", date);
        valuesMap.put("endDate", generateDateAfterGivenDate(date, 1));
        convertedJson = JsonUtil.readJsonFileAsString("json/Period.json", valuesMap);
        JsonNode period = getPeriodHelper().createPeriod(token, convertedJson);
        id = period.get("id").asText();
        System.out.println(id);
    }
}
