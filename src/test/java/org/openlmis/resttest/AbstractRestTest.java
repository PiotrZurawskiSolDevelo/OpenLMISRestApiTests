package org.openlmis.resttest;

import org.junit.Before;
import org.openlmis.resttest.helpers.FacilityHelper;
import org.openlmis.resttest.helpers.FacilityOperatorHelper;
import org.openlmis.resttest.helpers.FacilityTypeHelper;
import org.openlmis.resttest.helpers.GeographicLevelHelper;
import org.openlmis.resttest.helpers.GeographicZoneHelper;
import org.openlmis.resttest.helpers.PeriodHelper;
import org.openlmis.resttest.helpers.ProgramHelper;
import org.openlmis.resttest.helpers.ScheduleHelper;
import org.openlmis.resttest.helpers.TokenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;

public abstract class AbstractRestTest {

    private TokenHelper tokenHelper;
    private ProgramHelper programHelper;
    private GeographicLevelHelper geographicLevelHelper;
    private GeographicZoneHelper geographicZoneHelper;
    private FacilityTypeHelper facilityTypeHelper;
    private FacilityHelper facilityHelper;
    private ScheduleHelper scheduleHelper;
    private FacilityOperatorHelper facilityOperatorHelper;
    private PeriodHelper periodHelper;

    private Random rand = new Random();

    private String requisitionsUrl;
    private String authUrl;
    private String username;
    private String password;
    private String stringDay;

    private Integer day;

    @Before
    public void baseSetUp() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            requisitionsUrl = properties.getProperty("requisitions.api.url");
            authUrl = properties.getProperty("auth.api.url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            tokenHelper = new TokenHelper(authUrl);

            programHelper = new ProgramHelper(requisitionsUrl);
            geographicLevelHelper = new GeographicLevelHelper(requisitionsUrl);
            geographicZoneHelper = new GeographicZoneHelper(requisitionsUrl);
            facilityTypeHelper = new FacilityTypeHelper(requisitionsUrl);
            facilityHelper = new FacilityHelper(requisitionsUrl);
            scheduleHelper = new ScheduleHelper(requisitionsUrl);
            facilityOperatorHelper = new FacilityOperatorHelper(requisitionsUrl);
            periodHelper =  new PeriodHelper(requisitionsUrl);
        }
    }

    protected TokenHelper getTokenHelper() {
        return tokenHelper;
    }

    protected void setTokenHelper(TokenHelper tokenHelper) {
        this.tokenHelper = tokenHelper;
    }

    protected ProgramHelper getProgramHelper() {
        return programHelper;
    }

    protected void setProgramHelper(ProgramHelper programHelper) {
        this.programHelper = programHelper;
    }

    protected GeographicLevelHelper getGeographicLevelHelper() {
        return geographicLevelHelper;
    }

    protected void setGeographicLevelHelper(GeographicLevelHelper geographicLevelHelper) {
        this.geographicLevelHelper = geographicLevelHelper;
    }

    protected GeographicZoneHelper getGeographicZoneHelper() {
        return geographicZoneHelper;
    }

    protected void setGeographicZoneHelper(GeographicZoneHelper geographicZoneHelper) {
        this.geographicZoneHelper = geographicZoneHelper;
    }

    protected FacilityTypeHelper getFacilityTypeHelper() {
        return facilityTypeHelper;
    }

    protected void setFacilityTypeHelper(FacilityTypeHelper facilityTypeHelper) {
        this.facilityTypeHelper = facilityTypeHelper;
    }

    protected FacilityHelper getFacilityHelper() {
        return facilityHelper;
    }

    protected void setFacilityHelper(FacilityHelper facilityHelper) {
        this.facilityHelper = facilityHelper;
    }

    protected ScheduleHelper getScheduleHelper() {
        return scheduleHelper;
    }

    protected void setScheduleHelper(ScheduleHelper scheduleHelper) {
        this.scheduleHelper = scheduleHelper;
    }

    protected PeriodHelper getPeriodHelper() { return periodHelper; }

    protected void setPeriodHelper(PeriodHelper periodHelper) {
        this.periodHelper = periodHelper;
    }

    protected FacilityOperatorHelper getFacilityOperatorHelper() { return facilityOperatorHelper; }

    protected void setFacilityOperatorHelper(FacilityOperatorHelper facilityOperatorHelper) {
        this.facilityOperatorHelper = facilityOperatorHelper;
    }

    protected String getRequisitionsUrl() {
        return requisitionsUrl;
    }

    protected String getAuthUrl() {
        return authUrl;
    }

    protected String getUsername() {
        return username;
    }

    protected String getPassword() {
        return password;
    }

    protected String genrateDate() {
        day = rand.nextInt(20);
        Integer month = rand.nextInt(12);
        Integer year = rand.nextInt(17) + 2000;
        String stringMonth;
        if (day < 10) {
            stringDay = '0' + day.toString();
        } else {
            stringDay = day.toString();
        }
        if (month < 10) {
            stringMonth = '0' + month.toString();
        } else {
            stringMonth = month.toString();
        }
        return year.toString() + '-' + stringMonth + '-' + stringDay;
    }

    protected String generateDateAfterPreviousDate(String date) {
        String[] oldDate = date.split("-");
        day = Integer.parseInt(oldDate[2]) + 5;
        if(day <10) {
            stringDay = '0' + day.toString();
        } else {
            stringDay = day.toString();
        }
        return oldDate[0] + '-' + oldDate[1] + '-' + stringDay;
    }

    protected String addValuesToJson(String jsonName, String values) {
        return "";

    }
}
