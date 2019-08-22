package contact.tests;

import base.pages.BaseLandingPage;
import base.tests.BasicTest;
import contact.pages.ContactPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.logs.LogForTest;
import utils.verify.Verify;


public class ContactTest extends BasicTest {

    private ContactPage contactPage;
    private BaseLandingPage landingPage;

    @Override
    @BeforeMethod
    public void initPages() {
        contactPage = new ContactPage(driver);
        landingPage = new BaseLandingPage(driver);
    }

    @DataProvider
    public Object[][] getExpectedValidationErrors() {
        return new Object[][]{
                {"Validation errors empty fields", "", "", "", ""},
                {"Validation errors short data", "s", "s", "s", ""},
                {"Validation errors wrong data" ,"ss", "ss@qq#.com", "ss", ""},
                {"Validation errors valid data", "ss ss", "test@qq.com", "sss", "Message sent!"},
        };
    }

    @Test(dataProvider = "getExpectedValidationErrors", priority = 1)
    public void sendNewMessageValidationTest(String providerInfo, String userName, String email, String message,
                                             String error) {
        gotoPage(BASE_URL);
        landingPage.selectMenuSectionByName("Contact Us");
        waitForJSandJQueryToLoad();
        contactPage.inputUserName(userName);
        contactPage.inputEmail(email);
        contactPage.inputMessage(message);
        contactPage.clickSendButton();

        if (providerInfo.equals("Validation errors valid data")) {
            waitForJSandJQueryToLoad();
            LogForTest.info("Start checking validation error");
            Verify.verifyEquals(contactPage.getValidationError(), error, "Wrong error message");
        }
        contactPage.clickCloseButton();
    }
}
