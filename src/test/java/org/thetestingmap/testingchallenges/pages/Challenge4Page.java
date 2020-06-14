package org.thetestingmap.testingchallenges.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Challenge4Page extends BasePage {
    private final String PAGE_URL = BASE_URL + "/challenge4.php";

    private By firstNameField = By.xpath("//input[@class='inputbox']");
    private By submitButton = By.xpath("//input[@type='submit']");
    private By hiddenText = By.xpath("//span[@class='req']/preceding-sibling::font");

    public Challenge4Page(WebDriver driver, Logger log) {
        super(driver, log);
    }

    @Step
    public Challenge4Page openPage() {
        openUrl(PAGE_URL);
        return this;
    }

    @Step
    public Challenge4Page enterTestData() {
        type(getIdNumber(new int[]{2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9}), firstNameField);
        return this;
    }

    @Step
    public Challenge4Page clickSubmitButton() {
        log.info("Click Submit Button");
        click(submitButton);
        return this;
    }

    @Step
    public String getResultOfLoginMessage() {
        log.info("Result = " + find(hiddenText).getText());
        return find(hiddenText).getText();
    }
}
