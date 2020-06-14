package org.thetestingmap.testingchallenges.challenge_4;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.thetestingmap.testingchallenges.pages.Challenge4Page;
import org.thetestingmap.testingchallenges.testEngine.TestEngine;

@Listeners({org.thetestingmap.testingchallenges.testEngine.TestListener.class})
public class GenerateTestingDataTest extends TestEngine {

    @Test
    @Story("Checking testing data")
    @Description("Open page and check testing data")
    @Severity(SeverityLevel.NORMAL)
    public void checkingFiveIdNumbersTest() {
        for (int i = 0; i < 5; i++) {
            System.out.println(new  Challenge4Page(getDriver(), log)
                    .getIdNumber(new int[] {2, 7, 9, 1, 4, 6, 3, 5, 8, 2, 7, 9}));
             new Challenge4Page(getDriver(), log)
                    .openPage()
                    .enterTestData()
                    .clickSubmitButton();
        }
        Assert.assertTrue(new Challenge4Page(getDriver(), log).getResultOfLoginMessage().contains("YOU HAVE DONE IT"));
    }
}
