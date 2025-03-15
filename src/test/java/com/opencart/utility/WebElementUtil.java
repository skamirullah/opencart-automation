package com.opencart.utility;

import com.opencart.constants.Browser;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class WebElementUtil {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    Logger logger = LoggerUtil.getLogger(this.getClass());

    public WebDriver getDriver() {
        return driver.get();
    }

    public WebElementUtil(WebDriver driver) {
        this.driver.set(driver);
    }

    public WebElementUtil(String browserName){
        logger.info("Launching " + browserName + " browser");
        if(browserName.equalsIgnoreCase("chrome")){
            driver.set(new ChromeDriver());
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver.set(new FirefoxDriver());
        } else {
            logger.error("Please provide correct browser name !!");
            System.err.println("Please provide correct browser name !!");
        }
    }

    public WebElementUtil(Browser browserName){
        logger.info("Launching " + browserName + " browser");
        if(browserName == Browser.CHROME){
            driver.set(new ChromeDriver());
        } else if (browserName == Browser.FIREFOX) {
            driver.set(new FirefoxDriver());
        } else {
            System.out.println("Please provide correct browser name !!");
        }
    }

    public void navigateToUrl(String url){
        logger.info("Visiting the website " + url);
        driver.get().get(url);
    }

    public void maximizeWindow(){
        logger.info("Maximizing the browser window");
        driver.get().manage().window().maximize();
    }

    public void clickOn(By locator){
        logger.info("Finding element with locator " + locator);
        WebElement webElement = driver.get().findElement(locator);
        logger.info("Element found now trying to perform click");
        webElement.click();
    }

    public void enterText(By locator, String text){
        logger.info("Finding element with locator " + locator);
        WebElement element = driver.get().findElement(locator);
        logger.info("Element found now trying to enter text " + text);
        element.sendKeys(text);
    }
    public String getElementText(By locator){
        logger.info("Finding element with locator " + locator);
        WebElement element = driver.get().findElement(locator);
        logger.info("Element found now trying to get the value");
        return element.getText();
    }

    public String captureScreenshot(String name){
        WebDriver driverInstance = driver.get();
        TakesScreenshot screenshot = (TakesScreenshot) driverInstance;
        File screenshotData = screenshot.getScreenshotAs(OutputType.FILE);
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm-ss");
        String timeStamp = simpleDateFormat.format(date);
        String path = System.getProperty("user.dir") + "/screenshot/" + name + " - "+ timeStamp +".png";
        File screenshotFile = new File(path);
        try {
            FileUtils.copyFile(screenshotData, screenshotFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return path;


    }
}
