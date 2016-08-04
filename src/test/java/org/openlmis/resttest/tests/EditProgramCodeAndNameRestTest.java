package org.openlmis.resttest.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openlmis.resttest.AbstractRestTest;
import org.openlmis.resttest.util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * This test should create new Program, edit created program and check possibility to change Program code and Program name;
 */
public class EditProgramCodeAndNameRestTest extends AbstractRestTest {

    private static final String SERVER_URL = "http://10.222.17.187:8080";
    private String tokenValue = "?access_token=";
    private Map<String, String> valuesMap = new HashMap<>();

    @Before
    public void createToken() throws IOException {
        tokenValue += getTokenHelper().returnCreatedToken(SERVER_URL);
    }

    @Test //OLMIS-230
    public void createAndEditProgram() throws IOException {
        String value, id;
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
        String convertedJson = JsonUtil.readJsonFileAsString("Program.json", valuesMap);
        JsonNode program1 = getProgramHelper().createOrEditProgramUsingAllVariables(SERVER_URL, tokenValue, convertedJson);
        JsonNode links = program1.get("_links");
        JsonNode programJson = links.get("program");
        id = programJson.get("href").asText().substring((SERVER_URL + "/api/programs/").length());
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("id", id);
        convertedJson = JsonUtil.readJsonFileAsString("EditProgram.json", valuesMap);
        JsonNode program2 = getProgramHelper().createOrEditProgramUsingAllVariables(SERVER_URL, tokenValue, convertedJson);
        Assert.assertEquals(id, program2.get("_links").get("program").get("href").asText().substring((SERVER_URL + "/api/programs/").length()));
        Assert.assertNotEquals(program1.get("code").asText(), program2.get("code").asText());
        Assert.assertNotEquals(program1.get("name").asText(), program2.get("name").asText());
    }
}
