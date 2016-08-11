package org.openlmis.resttest;

import org.junit.Before;
import org.openlmis.resttest.helpers.FacilityHelper;
import org.openlmis.resttest.helpers.FacilityOperatorHelper;
import org.openlmis.resttest.helpers.FacilityTypeHelper;
import org.openlmis.resttest.helpers.GeographicLevelHelper;
import org.openlmis.resttest.helpers.GeographicZoneHelper;
import org.openlmis.resttest.helpers.PeriodHelper;
import org.openlmis.resttest.helpers.ProductCategoryHelper;
import org.openlmis.resttest.helpers.ProductHelper;
import org.openlmis.resttest.helpers.ProgramHelper;
import org.openlmis.resttest.helpers.ScheduleHelper;
import org.openlmis.resttest.helpers.TokenHelper;
import org.openlmis.resttest.helpers.UserHelper;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private ProductCategoryHelper productCategoryHelper;
    private UserHelper userHelper;
    private ProductHelper productHelper;

    private Random rand = new Random();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String requisitionsUrl;
    private String authUrl;
    private String username;
    private String password;

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
            userHelper = new UserHelper(requisitionsUrl);
            productCategoryHelper = new ProductCategoryHelper(requisitionsUrl);
            productHelper = new ProductHelper(requisitionsUrl);
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

    protected ProductCategoryHelper getProductCategoryHelper() { return productCategoryHelper; }

    protected void setProductCategoryHelper(ProductCategoryHelper productCategoryHelper) {
        this.productCategoryHelper = productCategoryHelper;
    }

    protected ProductHelper getProductHelper() { return productHelper; }

    protected void setProductHelper(ProductHelper productHelper) { this.productHelper = productHelper; }

    protected Random getRand() { return rand; }

    protected void setRand(Random rand) { this.rand = rand; }

    protected SimpleDateFormat getSimpleDateFormat() { return simpleDateFormat; }

    protected void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) { this.simpleDateFormat = simpleDateFormat; }

    protected UserHelper getUserHelper() { return userHelper; }

    protected void setUserHelper(UserHelper userHelper) { this.userHelper = userHelper; }

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
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 10);
        c.set(Calendar.MONTH, rand.nextInt(12)+1);
        c.set(Calendar.YEAR, 2016);
        return simpleDateFormat.format(c.getTime());
    }

    protected String generateDateAfterGivenDate(String date, Integer countDays) throws ParseException {
        Calendar c = Calendar.getInstance();
        c.setTime(simpleDateFormat.parse(date));
        c.add(Calendar.DATE, countDays);
        return simpleDateFormat.format(c.getTime());
    }

    protected String addValuesToJson(String jsonName, String values) {
        return "";
    }

    protected long calculateDifferenceBetweenDates(String startDate, String endDate) throws ParseException {
        Date date1 = simpleDateFormat.parse(startDate);
        Date date2 = simpleDateFormat.parse(endDate);
        long diffInMillies = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
    }
}
