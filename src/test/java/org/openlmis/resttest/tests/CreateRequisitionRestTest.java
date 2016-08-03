package org.openlmis.resttest.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Before;
import org.junit.Test;
import org.openlmis.resttest.helpers.FacilityHelper;
import org.openlmis.resttest.helpers.FacilityTypeHelper;
import org.openlmis.resttest.helpers.GeographicLevelHelper;
import org.openlmis.resttest.helpers.GeographicZoneHelper;
import org.openlmis.resttest.helpers.ProgramHelper;
import org.openlmis.resttest.helpers.ScheduleHelper;
import org.openlmis.resttest.helpers.TokenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CreateRequisitionRestTest {

    private static final String SERVER_URL = "http://10.222.17.187:";
    private static final Integer SERVER_PORT = 8080;
    private String tokenValue = "?access_token=";
    private TokenHelper tokenHelper = new TokenHelper();
    private ProgramHelper programHelper = new ProgramHelper();
    private GeographicLevelHelper geographicLevelHelper = new GeographicLevelHelper();
    private GeographicZoneHelper geographicZoneHelper = new GeographicZoneHelper();
    private FacilityTypeHelper facilityTypeHelper = new FacilityTypeHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();
    private ScheduleHelper scheduleHelper = new ScheduleHelper();
    private Map<String, String> valuesMap = new HashMap<>();
    private Random rand = new Random();
    @Before
    public void createToken() throws IOException {
        tokenValue += tokenHelper.returnCreatedToken(SERVER_URL);
        System.out.println("TOKEN: "+tokenValue);
    }

    @Test
    public void createRequisiton() throws IOException {
        String value;
        StrSubstitutor sub;
        String code = RandomStringUtils.randomAlphabetic(5);
        String name = RandomStringUtils.randomAlphabetic(5);
        String description = RandomStringUtils.randomAlphabetic(10);
        String active = "false";
        String periodsSkippable = "false";
        String showNonFullSupplyTab = "false";
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("description", description);
        valuesMap.put("active", active);
        valuesMap.put("periodsSkippable", periodsSkippable);
        valuesMap.put("showNonFullSupplyTab", showNonFullSupplyTab);
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("Program.json")) {
            value = IOUtils.toString(in, Charset.forName("UTF-8"));
        }
        sub = new StrSubstitutor(valuesMap);
        String convertedJson = sub.replace(value);
        JsonNode programJson = programHelper.createOrEditProgramUsingAllVariables(SERVER_URL, SERVER_PORT, tokenValue, convertedJson);

        valuesMap.clear();
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        Integer levelNumber = rand.nextInt(50);
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("levelNumber", levelNumber.toString());
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("GeographicLevel.json")) {
            value = IOUtils.toString(in, Charset.forName("UTF-8"));
        }
        sub = new StrSubstitutor(valuesMap);
        convertedJson = sub.replace(value);
        JsonNode geographicLevelJson = geographicLevelHelper.createGeographicLevel(SERVER_URL, SERVER_PORT,  tokenValue, convertedJson);
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
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("GeographicZone.json")) {
            value = IOUtils.toString(in, Charset.forName("UTF-8"));
        }
        sub = new StrSubstitutor(valuesMap);
        convertedJson = sub.replace(value);
        JsonNode geographicZoneJson = geographicZoneHelper.createGeographicZones(SERVER_URL, SERVER_PORT,  tokenValue, convertedJson);
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
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("FacilityType.json")) {
            value = IOUtils.toString(in, Charset.forName("UTF-8"));
        }
        sub = new StrSubstitutor(valuesMap);
        convertedJson = sub.replace(value);
        JsonNode facilityTypeJson = facilityTypeHelper.createFacilityType(SERVER_URL, SERVER_PORT,  tokenValue, convertedJson);
        String facilityTypesHref = facilityTypeJson.get("_links").get("facilityType").get("href").asText();
        JsonNode facilityJson = facilityHelper.createFacility(SERVER_URL, SERVER_PORT, tokenValue, facilityTypesHref, geographicZone);
        JsonNode scheduleJson = scheduleHelper.createSchedule(SERVER_URL, SERVER_PORT, tokenValue);

    }
}
