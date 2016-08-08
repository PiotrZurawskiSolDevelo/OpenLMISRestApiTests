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
 * This test should create new Program, edit the created program and check the possibility of changing both the Program code and Program name;
 */
public class EditProgramCodeAndNameRestTest extends AbstractRestTest {

    private String token;

    @Before
    public void setUp() throws IOException {
        token = getTokenHelper().returnCreatedToken(getUsername(), getPassword());
    }

    //OLMIS-230
    @Test
    public void createAndEditProgram() throws IOException {
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
        JsonNode program1 = getProgramHelper().createOrEditProgram(token, convertedJson);
        JsonNode links = program1.get("_links");
        JsonNode programJson = links.get("program");

        String id = programJson.get("href").asText().substring((getRequisitionsUrl() + "/api/programs/").length());
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);

        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("id", id);

        convertedJson = JsonUtil.readJsonFileAsString("json/EditProgram.json", valuesMap);
        JsonNode program2 = getProgramHelper().createOrEditProgram(token, convertedJson);

        Assert.assertEquals(id, program2.get("_links").get("program").get("href").asText().substring((getRequisitionsUrl() + "/api/programs/").length()));
        Assert.assertNotEquals(program1.get("code").asText(), program2.get("code").asText());
        Assert.assertNotEquals(program1.get("name").asText(), program2.get("name").asText());
    }
}
