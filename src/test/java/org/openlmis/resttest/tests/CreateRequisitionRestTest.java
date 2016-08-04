package org.openlmis.resttest.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.openlmis.resttest.AbstractRestTest;
import org.openlmis.resttest.util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateRequisitionRestTest extends AbstractRestTest {

    private String tokenValue = "?access_token=";

    private Random rand = new Random();

    @Before
    public void setUp() throws IOException {
        tokenValue += getTokenHelper().returnCreatedToken();
        System.out.println("TOKEN: " +tokenValue);
    }

    @Test
    public void createRequisition() throws IOException {
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
        String convertedJson = JsonUtil.readJsonFileAsString("Program.json", valuesMap);
        JsonNode programJson = getProgramHelper().createOrEditProgramUsingAllVariables(tokenValue, convertedJson);

        valuesMap.clear();
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        Integer levelNumber = rand.nextInt(50);
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("levelNumber", levelNumber.toString());
        convertedJson = JsonUtil.readJsonFileAsString("GeographicLevel.json", valuesMap);
        JsonNode geographicLevelJson = getGeographicLevelHelper().createGeographicLevel(tokenValue, convertedJson);
        String level = geographicLevelJson.get("_links").get("geographicLevelHelper").get("href").asText();

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
        convertedJson = JsonUtil.readJsonFileAsString("GeographicZone.json", valuesMap);
        JsonNode geographicZoneJson = getGeographicZoneHelper().createGeographicZones(tokenValue, convertedJson);
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
        convertedJson = JsonUtil.readJsonFileAsString("FacilityType.json", valuesMap);
        JsonNode facilityTypeJson = getFacilityTypeHelper().createFacilityType(tokenValue, convertedJson);
        String facilityTypesHref = facilityTypeJson.get("_links").get("facilityType").get("href").asText();
        JsonNode facilityJson = getFacilityHelper().createFacility(tokenValue, facilityTypesHref, geographicZone);
        JsonNode scheduleJson = getScheduleHelper().createSchedule(tokenValue);

    }
}
