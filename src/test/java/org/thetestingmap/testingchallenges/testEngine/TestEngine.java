package org.thetestingmap.testingchallenges.testEngine;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Listeners({org.thetestingmap.testingchallenges.testEngine.TestListener.class})
public class TestEngine {
    public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<>();
    public Logger log;
    public String testSuiteName;
    public String testName;
    public String testMethodName;

    @BeforeMethod(alwaysRun = true)
    //Parameter will get browser from testng.xml on which browser test to run
    @Parameters("browser")
    public void initDriver(Method method, @Optional("chrome")String browser, ITestContext ctx) throws MalformedURLException {
        this.testSuiteName = ctx.getCurrentXmlTest().getSuite().getName();
        this.testName = ctx.getCurrentXmlTest().getName();
        this.testMethodName=method.getName();
        this.log = LogManager.getLogger(testName);

        RemoteWebDriver driver = null;
        if (browser.equals("chrome")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("chrome");
            driver = new RemoteWebDriver(
                    URI.create("http://localhost:4444/wd/hub").toURL(),
                    capabilities
            );
        } else if (browser.equals("firefox")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName("firefox");
            driver = new RemoteWebDriver(
                    URI.create("http://localhost:4444/wd/hub").toURL(),
                    capabilities
            );
        }
        setWebDriver(driver);
        getDriver().manage().window().maximize();
    }

    public WebDriver getDriver() {
        return dr.get();
    }

    public void setWebDriver(RemoteWebDriver driver) {
        dr.set(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver(ITestResult result) {
        if(result.FAILURE == result.getStatus()){
            takeScreenshotToProjectDirectory();
        }
        log.info("Driver has been closed");
        getDriver().quit();
        dr.set(null);
    }

    /**
     * Take screenshot with time and
     */
    public void takeScreenshotToHDDCTmpDirectory() throws IOException {
        File screenshot = ((TakesScreenshot) dr.get()).getScreenshotAs(OutputType.FILE);
        Date dataNow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh_mm_ss");
        String dynamicNameForScreenshot = simpleDateFormat.format(dataNow) + ".png";
        FileHandler.copy(screenshot, new File("c:\\tmp\\" + dynamicNameForScreenshot));
    }

    /**
     * Makes screenshot and put it in special folders (sorted it by Date and Class)
     */
    public void takeScreenshotWithEnterName(String fileName){
        File screenshot = ((TakesScreenshot) dr.get()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")
                +File.separator+"test-output"
                +File.separator+"screenshots"
                +File.separator+getTodayDate()
                +File.separator+testSuiteName
                +File.separator+testName
                +File.separator+testMethodName
                +File.separator+getSystemTime()
                +" "+fileName+".png";
        try
        {
            FileUtils.copyFile(screenshot,new File (path));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Makes screenshot and put it in special folders (sorted it by Date and Class)
     */
    public void takeScreenshotToProjectDirectory(){
        File screenshot = ((TakesScreenshot) dr.get()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir")
                +File.separator+"test-output"
                +File.separator+"screenshots"
                +File.separator+getTodayDate()
                +File.separator+testSuiteName
                +File.separator+testName
                +File.separator+testMethodName
                +File.separator+getSystemTime()
                +" "+testName+"_"+testMethodName+".png";
        try
        {
            FileUtils.copyFile(screenshot,new File (path));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Return SimpleDate ddMMyyyy to using in takeScreenshot method
     */
    private String getTodayDate() {
        return(new SimpleDateFormat("ddMMyyyy").format(new Date()));
    }

    /**
     * Return SimpleDate HHmmssSSS to using in takeScreenshot method
     */
    private String getSystemTime(){
        return (new SimpleDateFormat("HHmmssSSS").format(new Date()));
    }

    /**
     * Get logs from browser console
     */
    public List<LogEntry> getBrowserLogs(){
        LogEntries log=getDriver().manage().logs().get("browser");
        return log.getAll();
    }
}