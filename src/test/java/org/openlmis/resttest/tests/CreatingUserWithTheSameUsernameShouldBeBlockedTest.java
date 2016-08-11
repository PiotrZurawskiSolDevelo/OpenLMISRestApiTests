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

/**
 * This test should create new Program, edit the created program and check the possibility to change Program code and Program name;
 */
public class CreatingUserWithTheSameUsernameShouldBeBlockedTest extends AbstractRestTest {

    private String token;

    @Before
    public void setUp() throws IOException {
        token = getTokenHelper().returnCreatedToken(getUsername(), getPassword());
    }

    //OLMIS-856
    @Test
    public void checkPossibilityToCreateUserWithTheSameUsername() throws IOException, ParseException {
        String username = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(8);
        String lastName = RandomStringUtils.randomAlphabetic(8);
        String active = "false";
        String verified =  "false";

        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("username", username);
        valuesMap.put("password", password);
        valuesMap.put("firstName", firstName);
        valuesMap.put("lastName", lastName);
        valuesMap.put("active", active);
        valuesMap.put("verified", verified);

        String convertedJson = JsonUtil.readJsonFileAsString("json/User/UserRequiredData.json", valuesMap);
        JsonNode user = getUserHelper().createUser(token, convertedJson);
        JsonNode userWithTheSameUsername = getUserHelper().createUser(token, convertedJson);
        Assert.assertEquals(username, user.get("username").asText());
        Assert.assertEquals("could not execute statement", userWithTheSameUsername.get("cause").get("message").asText());
    }
}
