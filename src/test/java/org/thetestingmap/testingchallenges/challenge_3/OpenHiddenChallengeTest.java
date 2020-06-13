package org.thetestingmap.testingchallenges.challenge_3;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.thetestingmap.testingchallenges.pages.Challenge2Page;
import org.thetestingmap.testingchallenges.pages.Challenge3Page;
import org.thetestingmap.testingchallenges.testEngine.TestEngine;

@Listeners({org.thetestingmap.testingchallenges.testEngine.TestListener.class})
public class OpenHiddenChallengeTest extends TestEngine{
        @Test
        @Story("Checking hidden page")
        @Description("Open page and check text")
        @Severity(SeverityLevel.MINOR)
        public void checkingHiddenPageTest() {
            String result = new Challenge3Page(getDriver(), log)
                    .openPage()
                    .getResultOfLoginMessage();
            Assert.assertTrue(result.contains("Add your name to the curious testers list"));
        }
}
