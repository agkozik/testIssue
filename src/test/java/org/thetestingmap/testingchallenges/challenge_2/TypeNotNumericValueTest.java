package org.thetestingmap.testingchallenges.challenge_2;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.thetestingmap.testingchallenges.pages.Challenge2Page;
import org.thetestingmap.testingchallenges.testEngine.TestEngine;

@Listeners({org.thetestingmap.testingchallenges.testEngine.TestListener.class})
public class TypeNotNumericValueTest extends TestEngine {
    @Test
    @Story("Get access to type string to numeric")
    @Description("Using string instead of numeric value")
    @Severity(SeverityLevel.MINOR)
    public void notNumericLoginTest() {
        String result = new Challenge2Page(getDriver(), log)
                .openPage()
                .changeAttributeValue()
                .enterValueToTheNumeralField()
                .clickSubmitButton()
                .getResultOfLoginMessage();
        Assert.assertTrue(result.contains("YOU HAVE DONE IT"));
    }
}
