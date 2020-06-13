package org.thetestingmap.testingchallenges.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

@Getter
public class Challenge2Page extends BasePage {
    private final String PAGE_URL = BASE_URL + "/challenge2.php";

    private By numericField = By.xpath("//input[@class='inputbox']");
    private By submitButton = By.xpath("//input[@type='submit']");
    private By hiddenText = By.xpath("//span/font[contains(text(), 'Please enter your name')]");

    public Challenge2Page(WebDriver driver, Logger log) {
        super(driver, log);
    }

    @Step
    public Challenge2Page openPage(){
        openUrl(PAGE_URL);
        return this;
    }

    @Step
    public Challenge2Page changeAttributeValue() {
        log.info("Change Attribute Type to string" );
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByClassName('inputbox')[0].setAttribute('type', 'string')");
        return this;
    }

    @Step
    public Challenge2Page enterValueToTheNumeralField() {
        log.info("Typing value 'cracked!'");
        type("cracked!", numericField);
        return this;
    }

    @Step
    public Challenge2Page clickSubmitButton(){
        log.info("Click Submit Button");
        click(submitButton);
        return this;
    }

    @Step
    public String getResultOfLoginMessage() {
        log.info("Checks found:="+ find(hiddenText).getText());
        log.info("Result = "+ find(hiddenText).getText());
        return find(hiddenText).getText();
    }
}
