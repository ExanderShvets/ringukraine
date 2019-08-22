package base.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class BaseLandingPage extends BasePage {

    @FindBy(xpath = "//nav[@class='menu']/a")
    private List<WebElement> MENU_SECTION_NAMES_LIST;

    protected EventFiringWebDriver driver;

    public BaseLandingPage(EventFiringWebDriver driver) {
        super(driver);
        this.driver = driver;
    }


    public ArrayList<String> getMenuSectionsNameList() {
        if (MENU_SECTION_NAMES_LIST.size() == 0) {
            Assert.fail("Can't find menu section names");
        }
        ArrayList<String> result = new ArrayList<>();
        MENU_SECTION_NAMES_LIST.forEach(name -> result.add(name.getText()));
        return result;
    }

    public String getUrlBySectionMenuName(String name) {
        for (WebElement webElement : MENU_SECTION_NAMES_LIST) {
            if (webElement.getText().equals(name)) {
                return webElement.getAttribute("href");
            }
        }
        return "Can't return order page URL by plan name";
    }

    public void selectMenuSectionByName(String name) {
        if (MENU_SECTION_NAMES_LIST.size() == 0) {
            Assert.fail("Can't find menu section names");
        }
        for (int i = 0; i < MENU_SECTION_NAMES_LIST.size(); i++) {
            if (MENU_SECTION_NAMES_LIST.get(i).getText().equalsIgnoreCase(name)) {
                click(MENU_SECTION_NAMES_LIST.get(i), "Can`t click menu section:" + name);
                return;
            }
        }
    }
 }
