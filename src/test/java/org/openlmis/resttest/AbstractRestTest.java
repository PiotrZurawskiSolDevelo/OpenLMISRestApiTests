package org.openlmis.resttest;

import org.openlmis.resttest.helpers.FacilityHelper;
import org.openlmis.resttest.helpers.FacilityTypeHelper;
import org.openlmis.resttest.helpers.GeographicLevelHelper;
import org.openlmis.resttest.helpers.GeographicZoneHelper;
import org.openlmis.resttest.helpers.ProgramHelper;
import org.openlmis.resttest.helpers.ScheduleHelper;
import org.openlmis.resttest.helpers.TokenHelper;

public abstract class AbstractRestTest {

    private TokenHelper tokenHelper = new TokenHelper();
    private ProgramHelper programHelper = new ProgramHelper();
    private GeographicLevelHelper geographicLevelHelper = new GeographicLevelHelper();
    private GeographicZoneHelper geographicZoneHelper = new GeographicZoneHelper();
    private FacilityTypeHelper facilityTypeHelper = new FacilityTypeHelper();
    private FacilityHelper facilityHelper = new FacilityHelper();
    private ScheduleHelper scheduleHelper = new ScheduleHelper();

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
}
