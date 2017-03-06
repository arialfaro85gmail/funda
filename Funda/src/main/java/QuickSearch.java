public class QuickSearch {

    private FundaDriver driver;

    private String logoFunda = "logo.funda";
    private String autocompletefirstOption = "autocomplete.firstOption";
    private String autocompleteNoExistingMessage = "autocomplete.NoExistingMessage";
    private String autocompleteClear ="autocomplete.clear";
    private String place = "field.place";
    private String filterDistance = "select.distance";
    private String filterMinimumRange="select.minimumRange";
    private String filterMaximumRange="select.maximumRange";
    private String lastSearch = "message.lastSearch";
    private String lastSearchLink = "link.last.Search";
    private String tabBuy ="tab.buy";
    private String tabRent="tab.rent";
    private String tabNewBuilding="tab.newBuilding";
    private String tabRecreation="tab.recreation";
    private String tabEurope="tab.Europa";
    private String buttonSearch= "button.search";
    private String resultsSort="results.sort";
    private String resultsCard="results.viewNotActive";
    private String resultsList="results.viewActive";

    public String getAutocompletefirstOption() {
        return autocompletefirstOption;
    }

    public String getLogoFunda() {
        return logoFunda;
    }

    public String getAutocompleteNoExistingMessage() {
        return autocompleteNoExistingMessage;
    }

    public String getAutocompleteClear() {
        return autocompleteClear;
    }

    public String getPlace() {
        return place;
    }

    public String getFilterDistance() {
        return filterDistance;
    }

    public String getFilterMinimumRange() {
        return filterMinimumRange;
    }

    public String getFilterMaximumRange() {
        return filterMaximumRange;
    }

    public String getLastSearch() {
        return lastSearch;
    }

    public String getLastSearchLink() {
        return lastSearchLink;
    }

    public String getTabBuy() {
        return tabBuy;
    }

    public String getTabRent() {
        return tabRent;
    }

    public String getTabNewBuilding() {
        return tabNewBuilding;
    }

    public String getTabRecreation() {
        return tabRecreation;
    }

    public String getTabEurope() {
        return tabEurope;
    }

    public String getButtonSearch() {
        return buttonSearch;
    }

    public String getResultsSort() {
        return resultsSort;
    }

    public String getResultsCard() {
        return resultsCard;
    }

    public String getResultsList() {
        return resultsList;
    }

    public FundaDriver getDriver(){
        return this.driver;
    }

    QuickSearch(FundaDriver driver){
        this.driver = driver;
    }

    public void navigateToHome(){
        driver.getElementWhenClickable(this.logoFunda).click();
    }

    public void cleanPage(){
        driver.deleteCookies();
        driver.refresh();
        driver.waitForPageLoad();
    }

    public void selectBuyTab(){
        driver.getElementWhenClickable(this.tabBuy).click();
    }

    public void selectRentTab(){
        driver.getElementWhenClickable(this.tabRent).click();
    }

    public void selectNewBuildingTab(){
        driver.getElementWhenClickable(this.tabNewBuilding).click();
    }

    public void selectRecreationTab(){
        driver.getElementWhenClickable(this.tabRecreation).click();
    }

    public void selectEuropaTab(){
        driver.getElementWhenClickable(this.tabEurope).click();
    }

    public void makeSearchByPlace(String place){
        driver.inputValue(place,this.place);
        driver.getElementWhenClickable(this.buttonSearch).click();
    }

    public void makeSearchByDistance(String distance){
        driver.selectByText(this.filterDistance,distance);
        driver.getElementWhenClickable(this.buttonSearch).click();
    }

    public void makeSearchByMinimumRange(String minimumRange){
        driver.selectByValue(this.filterMinimumRange,minimumRange);
        driver.getElementWhenClickable(this.buttonSearch).click();

    }

    public void makeSearchByMaximumRange(String maximumRange){
        driver.selectByValue(this.filterMaximumRange,maximumRange);
        driver.getElementWhenClickable(this.buttonSearch).click();
    }

    public void makeSearchByAllParameters(String place , String distance, String minimumRange, String maximumRange){
        driver.clickAndInputValue(place,this.place);
        driver.click(this.autocompletefirstOption);
        driver.selectByText(this.filterDistance,distance);
        driver.selectByValue(this.filterMinimumRange,minimumRange);
        driver.selectByValue(this.filterMaximumRange,maximumRange);
        driver.getElementWhenClickable(this.buttonSearch).click();
    }

    public void makeSearchWithoutParameters() {
        driver.getElementWhenClickable(this.buttonSearch).click();
    }

    public void makeSearchSelectingAutocomplete(String place) {
        driver.inputValue(place,this.place);
        driver.click(this.autocompletefirstOption);
        driver.getElementWhenClickable(this.buttonSearch).click();
    }

    public void waitForResults(){
        driver.getElementWhenClickable(this.resultsList);
    }
}
