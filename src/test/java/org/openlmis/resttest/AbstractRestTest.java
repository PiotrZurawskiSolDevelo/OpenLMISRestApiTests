package org.openlmis.resttest;

import org.junit.Before;
import org.openlmis.resttest.helpers.FacilityHelper;
import org.openlmis.resttest.helpers.FacilityTypeHelper;
import org.openlmis.resttest.helpers.GeographicLevelHelper;
import org.openlmis.resttest.helpers.GeographicZoneHelper;
import org.openlmis.resttest.helpers.ProgramHelper;
import org.openlmis.resttest.helpers.ScheduleHelper;
import org.openlmis.resttest.helpers.TokenHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractRestTest {

    private TokenHelper tokenHelper;
    private ProgramHelper programHelper;
    private GeographicLevelHelper geographicLevelHelper;
    private GeographicZoneHelper geographicZoneHelper;
    private FacilityTypeHelper facilityTypeHelper;
    private FacilityHelper facilityHelper;
    private ScheduleHelper scheduleHelper;

    private String requisitionsUrl;
    private String authUrl;

    @Before
    public void baseSetUp() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test-config.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            requisitionsUrl = properties.getProperty("requisitions.api.url");
            authUrl = properties.getProperty("auth.api.url");

            tokenHelper = new TokenHelper(authUrl);

            programHelper = new ProgramHelper(requisitionsUrl);
            geographicLevelHelper = new GeographicLevelHelper(requisitionsUrl);
            geographicZoneHelper = new GeographicZoneHelper(requisitionsUrl);
            facilityTypeHelper = new FacilityTypeHelper(requisitionsUrl);
            facilityHelper = new FacilityHelper(requisitionsUrl);
            scheduleHelper = new ScheduleHelper(requisitionsUrl);
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

    protected String getRequisitionsUrl() {
        return requisitionsUrl;
    }

    protected String getAuthUrl() {
        return authUrl;
    }
}
