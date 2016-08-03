package testy.tests;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import testy.methods.Program;
import testy.methods.Token;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * This test should create new Program, edit created program and check possibility to change Program code and Program name;
 */
public class EditProgramCodeAndNameRestTest {
    private static final String SERVER_URL = "http://10.222.17.187:";
    private static final Integer SERVER_PORT = 8080;
    private String tokenValue = "?access_token=";
    private Token token = new Token();
    private Program program = new Program();
    private Map<String, String> valuesMap = new HashMap<>();
    @Before
    public void createToken() throws IOException {
        tokenValue += token.returnCreatedToken(SERVER_URL);
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
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("Program.json")) {
            value = IOUtils.toString(in, Charset.forName("UTF-8"));
        }
        StrSubstitutor sub = new StrSubstitutor(valuesMap);
        String convertedJson = sub.replace(value);
        JsonNode program1 = program.createOrEditProgramUsingAllVariables(SERVER_URL, SERVER_PORT, tokenValue, convertedJson);
        JsonNode links = program1.get("_links");
        JsonNode programJson = links.get("program");
        id = programJson.get("href").asText().substring((SERVER_URL + SERVER_PORT + "/api/programs/").length());
        code = RandomStringUtils.randomAlphabetic(5);
        name = RandomStringUtils.randomAlphabetic(5);
        valuesMap.put("code", code);
        valuesMap.put("name", name);
        valuesMap.put("id", id);
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("EditProgram.json")) {
            value = IOUtils.toString(in, Charset.forName("UTF-8"));
        }
        sub = new StrSubstitutor(valuesMap);
        convertedJson = sub.replace(value);
        JsonNode program2 = program.createOrEditProgramUsingAllVariables(SERVER_URL, SERVER_PORT, tokenValue, convertedJson);
        Assert.assertEquals(id, program2.get("_links").get("program").get("href").asText().substring((SERVER_URL + SERVER_PORT + "/api/programs/").length()));
        Assert.assertNotEquals(program1.get("code").asText(), program2.get("code").asText());
        Assert.assertNotEquals(program1.get("name").asText(), program2.get("name").asText());
    }
}
