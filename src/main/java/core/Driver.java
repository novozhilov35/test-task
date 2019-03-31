package core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Driver {
    private static WebDriver driver;
    private static final String CHROMEDRIVER_PATH = "D:\\chromedriver.exe";
    static private final long WAIT_SECONDS = 60;

    private Driver(){
    }

    public static void init(){
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(WAIT_SECONDS, TimeUnit.SECONDS);
    }

    public static WebDriver getDriver(){
        if (driver == null) {
            throw new IllegalStateException("Драйвер не инициализирован");
        }
        return driver;
    }

    public static void goToURL(String url){
        driver.get(url);
    }

    public static String getCurrentURL(){
        return driver.getCurrentUrl();
    }

    public static void close(){
        driver.quit();
    }

    static public void waitLocated(String locator){
        WebDriverWait wait = new WebDriverWait(driver, WAIT_SECONDS);
        wait.ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(locator))));

    }
    static public void waitClickable(WebElement element){
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        WebDriverWait wait = new WebDriverWait(driver, WAIT_SECONDS);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(element));
    }
}
