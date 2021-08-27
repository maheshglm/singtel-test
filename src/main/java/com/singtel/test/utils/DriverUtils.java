package com.singtel.test.utils;

import com.google.common.base.Strings;
import com.singtel.test.CustomException;
import com.singtel.test.mdl.CustomExceptionType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
public class DriverUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DriverUtils.class);

    public static final int DEFAULT_EXPLICIT_WAIT_SECONDS = 5;
    public static final String ELEMENT_EXPECTED_TO_APPEAR_WITHIN_SECONDS_BUT_FAILED_TO_DO_SO = "Element [{}] expected to appear within {} seconds, but failed to do so";
    public static final String ELEMENT_IS_NOT_VISIBLE_IN_SEC = "Element is not visible in {} sec";
    public static final String URL_CANNOT_BE_EMPTY = "Url cannot be empty";
    public static final String DRIVER_IS_NOT_INITIALIZED = "Driver is not initialized";

    private WebDriver driver;

    public WebDriver getDriver() {
        if (driver != null) {
            return driver;
        } else {
            LOGGER.error(DRIVER_IS_NOT_INITIALIZED);
            throw new CustomException(CustomExceptionType.DRIVER_NOT_INITIALIZED, DRIVER_IS_NOT_INITIALIZED);
        }
    }

    public void configWebDriver(final String browser) {
        if ("chrome".equalsIgnoreCase(browser)) {
            LOGGER.info("Configuring Chrome driver");
            ChromeOptions chromeOptions = new ChromeOptions();
            WebDriverManager.chromiumdriver().setup();
            driver = new ChromeDriver(chromeOptions);

            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } else {
            LOGGER.warn("[{}] browser not configured", browser);
        }
    }

    public void takeScreenshot(final String filepath) throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        final File screenshotAs = screenshot.getScreenshotAs(OutputType.FILE);
        final File destFile = new File(filepath);
        FileUtils.copyFile(screenshotAs, destFile);
    }

    public void quitWebDriver() {
        if (null != getDriver()) {
            getDriver().quit();
        }
    }

    public void launchURL(final String url) {
        if (!Strings.isNullOrEmpty(url)) {
            getDriver().get(url);
        } else {
            LOGGER.error(URL_CANNOT_BE_EMPTY);
            throw new CustomException(CustomExceptionType.INCOMPLETE_PARAMS, URL_CANNOT_BE_EMPTY);
        }
    }

    public WebDriverWait getWebDriverWait(final Integer timeOutInSeconds) {
        return new WebDriverWait(getDriver(), timeOutInSeconds);
    }

    private FluentWait<WebDriver> wait(int timeOutInSec) {
        return getWebDriverWait(timeOutInSec)
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(WebDriverException.class);
    }

    public WebElement findElement(By by, int timeOutInSec) {
        try {
            return wait(timeOutInSec)
                    .pollingEvery(Duration.ofSeconds(2))
                    .until(driver -> (getDriver()).findElement(by));
        } catch (Exception ex) {
            LOGGER.error(ELEMENT_EXPECTED_TO_APPEAR_WITHIN_SECONDS_BUT_FAILED_TO_DO_SO, by.toString(), timeOutInSec, ex);
            throw new CustomException(CustomExceptionType.PROCESSING_FAILED, ELEMENT_EXPECTED_TO_APPEAR_WITHIN_SECONDS_BUT_FAILED_TO_DO_SO, by.toString(), timeOutInSec);
        }
    }

    public List<WebElement> findElements(By by, int timeOutInSec) {
        try {
            return wait(timeOutInSec)
                    .pollingEvery(Duration.ofSeconds(2))
                    .until(driver -> (getDriver()).findElements(by));
        } catch (Exception ex) {
            LOGGER.error(ELEMENT_EXPECTED_TO_APPEAR_WITHIN_SECONDS_BUT_FAILED_TO_DO_SO, by.toString(), timeOutInSec, ex);
            throw new CustomException(CustomExceptionType.PROCESSING_FAILED, ELEMENT_EXPECTED_TO_APPEAR_WITHIN_SECONDS_BUT_FAILED_TO_DO_SO, by.toString(), timeOutInSec);
        }
    }

    public WebElement waitTillVisible(By by, int timeOutInSec) {
        try {
            return wait(timeOutInSec)
                    .until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            LOGGER.error(ELEMENT_IS_NOT_VISIBLE_IN_SEC, timeOutInSec);
            throw new CustomException(CustomExceptionType.ELEMENT_NOT_VISIBLE, ELEMENT_IS_NOT_VISIBLE_IN_SEC, timeOutInSec);
        }
    }
}
