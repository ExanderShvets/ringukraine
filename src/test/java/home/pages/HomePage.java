package home.pages;

import base.pages.BaseLandingPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BaseLandingPage {

    @FindBy(xpath = "//nav[@class='menu']/a")
    private List<WebElement> MENU_SECTION_NAMES_LIST;
    @FindBy(xpath = "//section[@class='main_we_are row']//i")
    private List<WebElement> ICONS_LIST;
    @FindBy(xpath = "//section[@class='main_we_are row']//div/b")
    private List<WebElement> WE_ARE_SECTION_TITLES_TEXT;
    @FindBy(xpath = "//section[@class='main_we_are row']//div/br")
    private List<WebElement> WE_ARE_SECTION_TEXT;

    public HomePage (EventFiringWebDriver driver) {super(driver);}

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

    public boolean isIconPresentHomePage() {
        for (WebElement svg_icon : ICONS_LIST) {
            return svg_icon.isDisplayed();
        }
        return false;
    }

    public ArrayList<String> getWeAreSectionTitlesText() {
        if (WE_ARE_SECTION_TITLES_TEXT.size() == 0) {
            Assert.fail("Can't find we are section titles text");
        }
        ArrayList<String> result = new ArrayList<>();
        WE_ARE_SECTION_TITLES_TEXT.forEach(name -> result.add(name.getText()));
        return result;
    }

    public ArrayList<String> getWeAreSectionText() {
        if (WE_ARE_SECTION_TEXT.size() == 0) {
            Assert.fail("Can't find we are section text");
        }
        ArrayList<String> result = new ArrayList<>();
        WE_ARE_SECTION_TEXT.forEach(name -> result.add(name.getText()));
        return result;
    }
}
