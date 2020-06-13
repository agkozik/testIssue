package org.thetestingmap.testingchallenges.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Getter
public class Challenge1Page extends BasePage {
    public String pageURL = "http://testingchallenges.thetestingmap.org/index.php";

    private By firstNameField = By.xpath("//input[@id=\"firstname\"]");
    private By submitButton = By.xpath("//input[@type='submit']");
    private By checkedCases = By.xpath("//span[@class='values-tested']");

    public Challenge1Page(WebDriver driver, Logger log) {
        super(driver, log);
    }

    @Step
    public Challenge1Page openPage(){
        openUrl(pageURL);
        return this;
    }

    @Step
    public Challenge1Page enterUserNameAndPassword(String userName) {
        log.info("First Name=" + userName);
        type(userName, firstNameField);
        return this;
    }

    @Step
    public Challenge1Page clickSubmitButton(){
        log.info("Click Submit Button");
        click(submitButton);
        return this;
    }
}
