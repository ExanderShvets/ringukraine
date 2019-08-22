package base.pages;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import utils.DefaultWebElementActions;

public class BasePage extends DefaultWebElementActions {

    protected EventFiringWebDriver driver;

    public BasePage(EventFiringWebDriver driver)  {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
