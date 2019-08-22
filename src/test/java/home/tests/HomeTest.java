package home.tests;

import base.tests.BasicTest;
import home.pages.HomePage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.logs.LogForTest;
import utils.verify.Verify;

import java.util.ArrayList;

public class HomeTest extends BasicTest {

    private HomePage homePage;

    @Override
    @BeforeMethod
    public void initPages() { homePage = new HomePage(driver); }

    private ArrayList<String> getNavigationMenuNamesList() {
        return new ArrayList<String>() {
            {
                add("Our Story");
                add("Careers");
                add("Partners");
                add("Events");
                add("Contact us");
                add("Join us!");
            }
        };
    }

    @DataProvider
    public Object[][] getURLs() {
        return new Object[][]{
                {"Our Story", BASE_URL + "about/"},
                {"Careers", BASE_URL + "careers/"},
                {"Partners", BASE_URL + "partners/"},
                {"Events", BASE_URL + "events/"},
                {"Contact us", BASE_URL + "#contact"},
                {"Join us!", BASE_URL + "careers/#apply_form"},
        };
    }

    private ArrayList<String> getExpectedWeAreSectionTitlesText() {
        return new ArrayList<String>() {
            {
                add("researchers and developers");
                add("proud of solving challenging tasks");
                add("driven by Ring’s mission:");
            }
        };
    }

    @Test(priority = 1)
    public void checkMenuSectionNames() {
        gotoPage(BASE_URL);
        LogForTest.info("Start checking menu section names");
        Verify.verifyEquals(homePage.getMenuSectionsNameList(), getNavigationMenuNamesList(),
                "Not expected navigation menu names.");
    }

    @Test(dataProvider = "getURLs", priority = 2)
    public void checkMenusSectionURL(String sectionName, String url) {
        gotoPage(BASE_URL);
        LogForTest.info("Start checking menu section url`s");
        Verify.verifyEquals(homePage.getUrlBySectionMenuName(sectionName), url,
                "Not expected navigation menu url`s.");
    }

    @Test(priority = 3)
    public void checkWeAreSectionTitlesAndIcons() {
        gotoPage(BASE_URL);
        LogForTest.info("Start checking We are section titles text");
        Verify.verifyEquals(homePage.getWeAreSectionTitlesText(), getExpectedWeAreSectionTitlesText(),
                "Not expected we are section titles text");
        LogForTest.info("Start checking We are section is icons present");
        Verify.verifyTrue(homePage.isIconPresentHomePage(),
                "We are icons section icons not present on home page");
    }
}
