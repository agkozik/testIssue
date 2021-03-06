package org.thetestingmap.testingchallenges.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BasePage {
    protected final String BASE_URL = "http://testingchallenges.thetestingmap.org";
    protected WebDriver driver;
    protected Logger log;
    protected int WAIT_IN_SEC = 5;

    public BasePage(WebDriver driver, Logger log) {
        this.driver = driver;
        this.log = log;
    }

    /**
     * Open page by URL
     */
    @Step("Open page by URL")
    public void openUrl(String url) {
        driver.get(url);
    }

    /**
     * Find element by given locator find=driver.findElement(locator)
     */
    @Step("Look up element by locator")
    public WebElement find(By locator) {
        log.info("Find element by locator " + locator);
        return new WebDriverWait(driver, WAIT_IN_SEC)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Find all  elements (List<WebElement>) by given locator findAll=driver.findElementS(locator)
     */
    @Step("Look up all elements by locator")
    public List<WebElement> findAll(By locator) {
        log.info("Find element by locator " + locator);
        new WebDriverWait(driver, WAIT_IN_SEC)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
        return driver.findElements(locator);
    }

    /**
     * Click on Element with given locator when its visible
     */
    @Step("Click on element by locator {locator}")
    public void click(By locator) {
        waitForVisibilityOf(locator, WAIT_IN_SEC);
        find(locator).click();
    }

    /**
     * Type given text into element with given locator (analog sendKeys)
     */
    @Step("Enter {text} in text field element by locator {locator}")
    public void type(String text, By locator) {
        waitForVisibilityOf(locator);
        find(locator).sendKeys(text);
    }

    /**
     * Set Attribute value
     */
    public void setAttributeByLocator(By locator, String attName, String attValue) {
        makeElementVisibleByJavascript(find(locator));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", find(locator), attName, attValue);
    }

    public void makeElementVisibleByJavascript(final WebElement element) {
        String script = "let element = arguments[0];"
                + "element.style.display='inline';";
        ((JavascriptExecutor) driver).executeScript(script, element);
    }

    /**
     * Generate Random int in range
     */
    public int generateRandomInRange(int min, int max) {
        int diff = max - min;
        Random random = new Random();
        int i = random.nextInt(diff + 1);
        return i + min;
    }

    public String generateRandomAreaCode() {
        Random random = new Random();
        int i = random.nextInt(52) + 1;
        if (i < 10) {
            return "0" + i;
        }
        return String.valueOf(i);
    }

    public String generateRandomOrderNumber() {
        Random random = new Random();
        int i = random.nextInt(999) + 1;
        if (i < 10) {
            return "00" + i;
        } else if (i < 100) {
            return "0" + i;
        }
        return String.valueOf(i);
    }

    public LocalDate generateRandomDateInRange() {
        LocalDate startDate = LocalDate.of(1800, 1, 1);
        long start = startDate.toEpochDay();

        LocalDate endDate = LocalDate.now();
        long end = endDate.toEpochDay();

        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        return LocalDate.ofEpochDay(randomEpochDay);
    }

    public String defineYear(LocalDate randomDate) {
        LocalDate date1900 = LocalDate.of(1900, 1, 1);
        LocalDate date1800 = LocalDate.of(1800, 1, 1);
        LocalDate date2000 = LocalDate.of(2000, 1, 1);

        LocalDate date1999 = LocalDate.of(1999, 12, 31);
        LocalDate date1899 = LocalDate.of(1899, 12, 31);
        LocalDate date2099 = LocalDate.of(2099, 12, 31);

        if (randomDate.isAfter(date1900) && randomDate.isBefore(date1999)) {
            int randomValue = generateRandomInRange(1, 3);
            if (randomValue == 3) {
                return String.valueOf(generateRandomInRange(7, 9));
            }
            return String.valueOf(randomValue);
        } else if (randomDate.isAfter(date1800) && randomDate.isBefore(date1899)) {
            int randomValue = generateRandomInRange(3, 5);
            if (randomValue == 5) {
                return String.valueOf(generateRandomInRange(7, 9));
            }
            return String.valueOf(randomValue);
        } else if (randomDate.isAfter(date2000) && randomDate.isBefore(date2099)) {
            int randomValue = generateRandomInRange(5, 7);
            if (randomValue == 7) {
                return String.valueOf(generateRandomInRange(7, 9));
            }
            return String.valueOf(randomValue);
        }
        return "Null!!!!!!!!!";
    }

    public String getCurrentYear(LocalDate randomDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        return randomDate.format(formatter);
    }

    public String getCurrentMonth(LocalDate randomDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
        return randomDate.format(formatter);
    }

    public String getCurrentDay(LocalDate randomDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
        return randomDate.format(formatter);
    }

    public String getIdNumber(int[] sole){
        int x = 0;
        String identificationNumber;
        LocalDate randomDate = generateRandomDateInRange();
        String numberId = defineYear(randomDate)
                + getCurrentYear(randomDate)
                + getCurrentMonth(randomDate)
                + getCurrentDay(randomDate)
                + generateRandomAreaCode()
                + generateRandomOrderNumber();

        int[] ncpArr = Arrays
                .stream(numberId.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int i = 0; i < ncpArr.length; i++) {
            x = x + (ncpArr[i] * sole[i]);
        }

        if (x % 11 == 10) {
            identificationNumber = Arrays.toString(ncpArr).replaceAll("\\[|\\]|,|\\s", "") + "1";
            System.out.println(identificationNumber);
        } else {
            identificationNumber = Arrays.toString(ncpArr).replaceAll("\\[|\\]|,|\\s", "") + x % 11;
        }
        return identificationNumber;
    }

    /**
     * Type text using actions
     */
    @Step
    public void pressKeysWithActions(Keys key) {
        log.info("Press keys " + key + " using Actions");
        Actions actions = new Actions(driver);
        actions.sendKeys(key).build().perform();
    }

    @Step
    public void pressKey(By locator, Keys key) {
        find(locator).sendKeys(key);
    }

    /**
     * Select all Ctrl+a
     */
    @Step
    public BasePage pressCtrlA(WebElement webElement) {
        String action = Keys.chord(Keys.CONTROL, "a");
        webElement.sendKeys(action);
        return this;
    }

    /**
     * Select all Ctrl+x
     */
    @Step
    public BasePage pressCtrlX(WebElement webElement) {
        String action = Keys.chord(Keys.CONTROL, "x");
        webElement.sendKeys(action);
        return this;
    }

    /**
     * Paste Ctrl+v
     */
    @Step
    public BasePage pressCtrlV(WebElement webElement) {
        String action = Keys.chord(Keys.CONTROL, "v");
        webElement.sendKeys(action);
        return this;
    }

    /**
     * Copy Ctrl+c
     */
    @Step
    public BasePage pressCtrlC(WebElement webElement) {
        String action = Keys.chord(Keys.CONTROL, "c");
        webElement.sendKeys(action);
        return this;
    }

    /**
     * Get Current URL from browser
     */
    @Step
    public String getCurrentUrlFromBrowser() {
        return driver.getCurrentUrl();
    }

    /**
     * Get current Page Title, from current page
     */
    @Step
    public String getCurrentPageTitle() {
        new WebDriverWait(driver, WAIT_IN_SEC)
                .until(ExpectedConditions.urlContains("the-internet.herokuapp.com"));
        return driver.getTitle();
    }

    /**
     * Get current PageSource, from current page
     */
    @Step
    public String getCurrentPageSource() {
        return driver.getPageSource();
    }

    /**
     * Wait for custom ExpectedCondition with ExplicitWait in seconds
     */
    @Step("Explicit wait by condition {condition}")
    public void waitFor(ExpectedCondition<WebElement> condition, Integer timeOutInSeconds) {
        timeOutInSeconds = timeOutInSeconds != null ? timeOutInSeconds : 30;
        new WebDriverWait(driver, timeOutInSeconds).until(condition);
    }

    /**
     * Wait for given number of seconds (ExplicitWait visibilityOfElementLocated(locator))
     */
    @Step("Wait For Visibility Of {locator} for {timeOutInSeconds}")
    public void waitForVisibilityOf(By locator, Integer... timeOutInSeconds) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                waitFor(ExpectedConditions.visibilityOfElementLocated(locator),
                        (timeOutInSeconds.length > 0 ? timeOutInSeconds[0] : null));
                break;
            } catch (StaleElementReferenceException e) {
                log.info(e.getLocalizedMessage());
            }
            attempts++;
        }
    }

    /**
     * Wait for Alert and swith to it
     */
    @Step
    public Alert switchToAlert() {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_IN_SEC);
        wait.until(ExpectedConditions.alertIsPresent());
        return driver.switchTo().alert();
    }

    /**
     * Switch to new window in browser by title
     */
    @Step("Switch To New Window By Title: ' {expectedTitle} ' ")
    public void switchToNewWindowByTitle(String expectedTitle) {
        String firstWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String iWindow : allWindows) {
            if (!(iWindow.equals(firstWindow))) {
                driver.switchTo().window(iWindow);
                if (driver.getTitle().equals(expectedTitle)) {
                    break;
                }
            }
        }
    }

    /**
     * Switch To Frame by locator
     */
    @Step("Switch To Frame By Locator: ' {locator} ' ")
    public void switchToFrameByLocator(By locator) {
        driver.switchTo().frame(find(locator));
    }

    /**
     * Scroll page to the bottom using JavaScript Executor
     */
    @Step
    public void scrollToBottom() {
        log.info("Scrolling to the bottom...");
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
}