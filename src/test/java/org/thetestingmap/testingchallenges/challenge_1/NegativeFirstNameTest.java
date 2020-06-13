package org.thetestingmap.testingchallenges.challenge_1;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.thetestingmap.testingchallenges.pages.Challenge1Page;
import org.thetestingmap.testingchallenges.testEngine.CsvDataProviders;
import org.thetestingmap.testingchallenges.testEngine.TestEngine;

import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Listeners({org.thetestingmap.testingchallenges.testEngine.TestListener.class})
public class NegativeFirstNameTest extends TestEngine {
    @Test(priority = 1, dataProvider = "csvReader", dataProviderClass = CsvDataProviders.class)
    public void negativeLogInTest(Map<String, String> testData) {
        String username  = testData.get("firstName");

        // Open main page
        Challenge1Page challenge1Page = new Challenge1Page(getDriver(), log);
        challenge1Page.openPage();

        // Execute negative First Name
        challenge1Page.enterUserNameAndPassword(username);
        challenge1Page.clickSubmitButton();

        // wait for message
//        challenge1Page.waitForMessage();
//        String message = challenge1Page.getTextMessageFromPage();
//        // Verification
//        Assert.assertTrue(message.contains(expectedErrorMessage), "Message doesn't contain expected text.");
    }
}
