package org.thetestingmap.testingchallenges.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Challenge3Page extends BasePage {
    private final String PAGE_URL = BASE_URL + "/challenge3.php";

    private By hiddenText = By.xpath("//span[@class='req']/preceding-sibling::font");

    public Challenge3Page(WebDriver driver, Logger log) {
        super(driver, log);
    }

    @Step
    public Challenge3Page openPage(){
        openUrl(PAGE_URL);
        return this;
    }

    @Step
    public String getResultOfLoginMessage() {
        log.info("Result = "+ find(hiddenText).getText());
        return find(hiddenText).getText();
    }
}
