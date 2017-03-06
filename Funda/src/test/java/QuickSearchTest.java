import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

/**
 * Created by ariana on 3/5/17.
 */
public class QuickSearchTest {
    FundaDriver driver;
    private Assertion assertAndStop = new Assertion();
    private SoftAssert assertAndContinue = new SoftAssert();
    private QuickSearch quickSearch;

    private void assertResultsPage() {
        assertAndContinue.assertTrue(driver.isElementPresent(this.quickSearch.getResultsSort()),
                "Sorting Element is not present");
        assertAndContinue.assertTrue(driver.isElementPresent(this.quickSearch.getResultsList()),
                "List Option Element is not present");
        assertAndContinue.assertTrue(driver.isElementPresent(this.quickSearch.getResultsCard()),
                "Card Option Element is not present");
    }

    @BeforeClass
    public void before()  {
        driver = new FundaDriver();
        driver.get(driver.getProperty("url.Prod"));
        driver.waitForPageLoad();
        this.quickSearch = new QuickSearch(driver);
        assertAndStop.assertTrue(driver.isElementPresent("logo.funda"),
                "Funda logo is not present");
    }

    @Test(priority=0)
    public void search_Buy_Non_Existing_Place(){
        String expectedWhenNothing = "Ai! Deze locatie kunnen we helaas niet vinden.";
        quickSearch.navigateToHome();
        quickSearch.selectBuyTab();
        quickSearch.makeSearchByPlace("NotExistingPlace");
        assertAndStop.assertTrue(driver.isElementPresent(quickSearch.getAutocompleteNoExistingMessage()),
                "Autocomplete message about Not Existing Place is not present");
        assertAndContinue.assertEquals(driver.getText(quickSearch.getAutocompleteNoExistingMessage()),expectedWhenNothing ,
                "Message about not existing place is not correct");
        assertAndContinue.assertAll();
    }

    @Test(priority=1)
    public void search_Buy_Do_Not_Input_Any_Parameters() {
        String expectedWhenNoParameters = "Land Nederland";
        String expectedURL = "heel-nederland";

        quickSearch.navigateToHome();
        quickSearch.selectBuyTab();
        quickSearch.makeSearchWithoutParameters();
        quickSearch.waitForResults();

        assertResultsPage();
        assertAndContinue.assertEquals(driver.getValue(this.quickSearch.getPlace()),expectedWhenNoParameters ,
                "Default Search Value is not correct");
        assertAndContinue.assertTrue(driver.urlContains(expectedURL),
                        "URL is not the one expected");
        assertAndContinue.assertAll();
    }


    @Test(priority=2)
    public void search_Buy_By_Just_Input_Distance()  {
        String distance = "+ 2 km";
        this.quickSearch.navigateToHome();
        this.quickSearch.selectBuyTab();
        this.quickSearch.makeSearchByDistance(distance);
        this.quickSearch.waitForResults();
        assertResultsPage();
        assertAndContinue.assertTrue(driver.urlContains(distance.replaceAll("\\s","")),
                "URL does not contain " + distance.replaceAll("\\s",""));
        assertAndContinue.assertAll();
    }

    @Test(priority=3)
    public void search_Buy_By_Just_Input_Minimun_Range()  {
        String minimumRange = "50000";
        this.quickSearch.navigateToHome();
        this.quickSearch.selectBuyTab();
        this.quickSearch.makeSearchByMinimumRange(minimumRange);
        this.quickSearch.waitForResults();

        assertResultsPage();
        assertAndContinue.assertTrue(driver.urlContains(minimumRange + "+"),
                "URL does not contain"+ minimumRange +"+");
        assertAndContinue.assertAll();
    }

    @Test(priority=4)
    public void search_Buy_By_Just_Input_Maximum_Range(){

        String maximumRange = "900000";
        this.quickSearch.navigateToHome();
        this.quickSearch.selectBuyTab();
        this.quickSearch.makeSearchByMaximumRange(maximumRange);
        this.quickSearch.waitForResults();

        assertResultsPage();
        assertAndContinue.assertTrue(driver.urlContains("0-"+maximumRange),
                "URL does not contain " + "0-"+maximumRange);
        assertAndContinue.assertAll();
    }

    @Test(priority=5)
    public void search_Buy_With_AllParameters(){

        String minimumRange = "50000";
        String maximumRange = "900000";
        String distance = "+ 2 km";
        String place = "Amsterdam";

        this.quickSearch.navigateToHome();
        this.quickSearch.selectBuyTab();
        this.quickSearch.makeSearchByAllParameters(place,distance,minimumRange,maximumRange);
        this.quickSearch.waitForResults();


        assertResultsPage();
        assertAndContinue.assertEquals(driver.getValue(this.quickSearch.getPlace()),place ,
                "Search Value is not correct");
        assertAndContinue.assertTrue(driver.urlContains(place),
                "URL is not the one expected");
        assertAndContinue.assertTrue(driver.urlContains(distance.replaceAll("\\s","")),
                "URL does not contain "+ distance.replaceAll("\\s",""));
        assertAndContinue.assertTrue(driver.urlContains(minimumRange + "-" + maximumRange),
                "URL does not contain the minimum and maximum range correctly" );
        assertAndContinue.assertAll();
    }


    @Test(priority=6)
    public void search_Buy_Last_Search_Not_Populated_First_Time(){
        quickSearch.navigateToHome();
        quickSearch.cleanPage();
        assertAndStop.assertFalse(driver.isElementPresent(quickSearch.getLastSearch()),
                "Last Search was found, it should not be present at this point");
        assertAndStop.assertFalse(driver.isElementPresent(quickSearch.getLastSearchLink()),
                "Last Search link was found, it should not be present at this point");
    }

    @Test(priority=7)
    public void search_Buy_By_Input_Place() {
        String place = "Amsterdam";

        quickSearch.navigateToHome();
        quickSearch.selectBuyTab();
        quickSearch.makeSearchSelectingAutocomplete(place);
        quickSearch.waitForResults();
        assertResultsPage();
        assertAndContinue.assertEquals(driver.getValue(this.quickSearch.getPlace()), place ,
                "The value on the search field is not correct");
        assertAndContinue.assertTrue(driver.urlContains(place),
                "URL is not the one expected");
        assertAndContinue.assertAll();
    }

    @Test(priority=8)
    public void search_Buy_Last_Search_Populated(){
        quickSearch.navigateToHome();
        quickSearch.selectBuyTab();
        assertAndContinue.assertTrue(driver.isElementPresent(this.quickSearch.getLastSearch()),
                "Last Search was found, it should not be present at this point");
        assertAndContinue.assertTrue(driver.isElementPresent(this.quickSearch.getLastSearchLink()),
                "Last Search link was found, it should not be present at this point");
    }

    @AfterClass
    public void after(){
        driver.quit();
    }
}
