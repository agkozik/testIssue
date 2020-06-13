package org.thetestingmap.testingchallenges.challenge_1;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.thetestingmap.testingchallenges.pages.Challenge1Page;
import org.thetestingmap.testingchallenges.testEngine.CsvDataProviders;
import org.thetestingmap.testingchallenges.testEngine.TestEngine;

import java.util.Map;

@Listeners({org.thetestingmap.testingchallenges.testEngine.TestListener.class})
public class NegativeFirstNameTest extends TestEngine {
    @Test(priority = 1, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    @Story("Checking First User Names")
    @Description("Using 17 values fron csv file")
    @Severity(SeverityLevel.MINOR)
    public void negativeLogInTest(Map<String, String> testData) {
        String username  = testData.get("firstName");

        // Open main page, type negative First Name into login field
        int checksFound = new Challenge1Page(getDriver(), log)
                .openPage()
                .enterUserNameAndPassword(username)
                .clickSubmitButton()
                .getfirstResultOfLoginMessages();
        Assert.assertNotEquals(checksFound, 0);
    }
}
