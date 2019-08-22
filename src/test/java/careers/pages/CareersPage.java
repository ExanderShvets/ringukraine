package careers.pages;

import base.pages.BaseLandingPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class CareersPage extends BaseLandingPage {

    @FindBy(xpath = "//div[@class='switcher_links']/a")
    private List<WebElement> MENU_SECTION_NAMES_LIST;

    public CareersPage (EventFiringWebDriver driver) { super(driver); }

    @Override
    public ArrayList<String> getMenuSectionsNameList() {
        if (MENU_SECTION_NAMES_LIST.size() == 0) {
            Assert.fail("Can't find menu section names");
        }
        ArrayList<String> result = new ArrayList<>();
        MENU_SECTION_NAMES_LIST.forEach(name -> result.add(name.getText()));
        return result;
    }

    @Override
    public String getUrlBySectionMenuName(String name) {
        for (WebElement webElement : MENU_SECTION_NAMES_LIST) {
            if (webElement.getText().equals(name)) {
                return webElement.getAttribute("href");
            }
        }
        return "Can't return order page URL by plan name";
    }
}
