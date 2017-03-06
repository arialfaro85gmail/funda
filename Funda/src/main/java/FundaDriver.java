import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;



public class FundaDriver {
    private WebDriver driver ;
    private Properties properties;
    private static final int TIMEOUT =  5;

    public FundaDriver()  {
        this.properties = new Properties();
        loadProperties("funda.properties");
        this.driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    public void refresh(){
        this.driver.navigate().refresh();
    }

    public void deleteCookies(){
        this.driver.manage().deleteAllCookies();
    }

    public String getValue(String key){
        return this.findElement(key).getAttribute("value");
    }

    public String getText(String key){
        return this.findElement(key).getText();
    }

    public void loadProperties(String prop)  {
        InputStream resourceStream =  getClass().getClassLoader().getResourceAsStream(prop);
        try {
            this.properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WebElement getElementWhenClickable(String key) {
        try {
            WebElement myDynamicElement = null;
            myDynamicElement = (new WebDriverWait(driver, TIMEOUT))
                    .until(ExpectedConditions.elementToBeClickable(this.getLocator(this.getProperty(key))));
            return myDynamicElement;
        } catch (Exception e) {
            System.out.println(key  + " element was not found or is not clickable.");
        }
        return null;
    }

    public boolean isElementPresent(String key) {
       if(this.findElement(key) != null )
           return true;
        return false;
    }

    public void inputValue(String value, String key) {
        WebElement element =  this.findElement(key);
        element.clear();
        element.sendKeys(value);
    }

    public void click(String key) {
        this.findElement(key).click();
    }

    public void clickAndInputValue(String value, String key)  {
        WebElement element =  this.findElement(key);
        element.click();
        element.clear();
        element.sendKeys(value);
    }

    public String getProperty(String key){
        return this.properties.getProperty(key);
    }

    public void quit(){
        this.driver.quit();
    }

    public WebElement findElement(String key)  {
        String locator = this.properties.getProperty(key);
        try {
        By type = null;
        type = this.getLocator(locator);
        return this.driver.findElement(type);

        } catch (Exception e) {
            return null;
        }
    }

    public void get(String url){
        this.driver.get(url);
    }

    public void selectByText(String key, String text){
        new Select(this.findElement(key)).selectByVisibleText(text);
    }

    public void selectByValue(String key, String value ){
        new Select(this.findElement(key)).selectByValue(value);
    }

    public By getLocator(String locator)  {
        String locatorType = locator.split(":")[0];
        String locatorValue = locator.split(":")[1];
        String s = locatorType.toLowerCase();
        if (s.equals("id")) {
            return By.id(locatorValue);
        } else if (s.equals("name")) {
            return By.name(locatorValue);
        } else if (s.equals("class")) {
            return By.className(locatorValue);
        } else if (s.equals("tagname")) {
            return By.tagName(locatorValue);
        } else if (s.equals("linktext")) {
            return By.linkText(locatorValue);
        } else if (s.equals("partiallinktext")) {
            return By.partialLinkText(locatorValue);
        } else if (s.equals("css")) {
            return By.cssSelector(locatorValue);
        } else if (s.equals("xpath")) {
            return By.xpath(locatorValue);
        } else {
            return null;
        }
    }

    public boolean urlContains(String expectedUrl){
        return this.driver.getCurrentUrl().toLowerCase().contains(expectedUrl.toLowerCase());
    }

    public void waitForPageLoad() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
