package careers.tests;

import base.pages.BaseLandingPage;
import base.tests.BasicTest;
import careers.pages.CareersPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.logs.LogForTest;
import utils.verify.Verify;

import java.util.ArrayList;

public class CareersTest extends BasicTest {

    private CareersPage careersPage;
    private BaseLandingPage landingPage;

    @Override
    @BeforeMethod
    public void initPages() {
        careersPage = new CareersPage(driver);
        landingPage = new BaseLandingPage(driver);
    }

    private ArrayList<String> getSwitcherNamesList() {
        return new ArrayList<String>() {
            {
                add("All");
                add("QA");
                add("Engineering");
                add("Infrastructure");
                add("Research");
                add("Support");
                add("Management");
                add("Operations");
                add("Other");
            }
        };
    }

    @DataProvider
    public Object[][] getURLs() {
        return new Object[][]{
                {"All", BASE_URL + "careers/#all"},
                {"QA", BASE_URL + "careers/#qa"},
                {"Engineering", BASE_URL + "careers/#engineering"},
                {"Infrastructure", BASE_URL + "careers/#infrastructure"},
                {"Research", BASE_URL + "careers/#research"},
                {"Support", BASE_URL + "careers/#support"},
                {"Management", BASE_URL + "careers/#management"},
                {"Operations", BASE_URL + "careers/#operations"},
                {"Other", BASE_URL + "careers/#other"},
        };
    }

    @Test(priority = 1)
    public void checkMenuSectionNames() {
        gotoPage(BASE_URL);
        landingPage.selectMenuSectionByName("Careers");
        waitForJSandJQueryToLoad();
        LogForTest.info("Start checking menu section names");
        Verify.verifyEquals(careersPage.getMenuSectionsNameList(), getSwitcherNamesList(),
                "Not expected navigation menu names.");
    }

    @Test(dataProvider = "getURLs", priority = 2)
    public void checkMenusSectionURL(String sectionName, String url) {
        gotoPage(BASE_URL + "careers/");
        waitForJSandJQueryToLoad();
        LogForTest.info("Start checking menu section url`s");
        Verify.verifyEquals(careersPage.getUrlBySectionMenuName(sectionName), url,
                "Not expected navigation menu url`s.");
    }
}
