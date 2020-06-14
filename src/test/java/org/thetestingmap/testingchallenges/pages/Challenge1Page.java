package org.thetestingmap.testingchallenges.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Getter
public class Challenge1Page extends BasePage {
    private final String PAGE_URL = BASE_URL + "/index.php";

    private By firstNameField = By.xpath("//input[@id=\"firstname\"]");
    private By submitButton = By.xpath("//input[@type='submit']");
    private By checkedCases = By.xpath("//span[@class='values-tested']");
    private By firstResultOfLogin = By.xpath("//ul[@class='values-description t10']/li[1]");
    private By xxx = By.xpath("//input[@name='user_right_as_admin']");

    public Challenge1Page(WebDriver driver, Logger log) {
        super(driver, log);
    }

    @Step
    public Challenge1Page openPage(){
        openUrl(PAGE_URL);
        return this;
    }

    @Step
    public Challenge1Page changeValueAttribute(){
        setAttributeByLocator(xxx, "value", "1");
        return this;
    }

    @Step
    public Challenge1Page changeAttributeValueByClassName() {
        log.info("Change Attribute Type to string" );
        String script = "let element = arguments[0];"
                + "element.style.display='visible';"
                ;
        ((JavascriptExecutor)driver).executeScript(script, find(xxx));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementsByClassName('user_right_as_admin')[0].setAttribute('value', '1')");
        return this;
    }

    @Step
    public Challenge1Page enterFirstName(String userName) {
        log.info("First Name=" + userName);
        type(userName, firstNameField);
        return this;
    }

    @Step
    public Challenge1Page enterAdminFirstName() {
        String value = "admin";
        log.info("First Name="+value );
        type(value, firstNameField);
        return this;
    }

    @Step
    public Challenge1Page clickSubmitButton(){
        log.info("Click Submit Button");
        click(submitButton);
        return this;
    }

    @Step
    public int getQuantityOfChecks() {
        log.info("Checks found:="+ find(checkedCases).getText());
        log.info("Check = "+ find(firstResultOfLogin).getText());
        return Integer.parseInt(find(checkedCases).getText());
    }

    @Step
    public String getFirstResultOfLoginMessages() {
        log.info("Message = "+ find(firstResultOfLogin).getText());
        return find(firstResultOfLogin).getText();
    }
}
