package products.doorbells.pages;

import base.pages.BasePlanPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import utils.logs.LogForTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DoorBellsPlanPage extends BasePlanPage {

    @FindBy(xpath = "//div[@class='h3'][contains(text(),'Door')]")
    private List<WebElement> PRODUCT_PLAN_NAMES;
    @FindBy(xpath = "//*[@class='plp_button']")
    private List<WebElement> BUY_NOW_BUTTONS;
    @FindBy(xpath = "//div[@class='truste_box_overlay']")
    private WebElement FRAME;
    @FindBy(xpath = "//a[@class='call']")
    private WebElement CLOSE_BUTTON;


    public DoorBellsPlanPage (EventFiringWebDriver driver) {super(driver);}

    public ArrayList<String> getProductNames() {
        if (PRODUCT_PLAN_NAMES.size() == 0) {
            Assert.fail("Can't find product plan names");
        }
        ArrayList<String> result = new ArrayList<>();
        PRODUCT_PLAN_NAMES.forEach(planName -> result.add(planName.getText()));
        return result;
    }

    public ArrayList<String> getFeaturesTextListByPlanName(String planName) {
        int planNumber = 0;
        for (int i = 0; i < PRODUCT_PLAN_NAMES.size(); i++) {
            if (PRODUCT_PLAN_NAMES.get(i).getText().equals(planName)) {
                planNumber = i + 1;
                break;
            }
        }
        String xPath = "(//div[contains(@class,'product cflex')])[" + planNumber + "]//div[@class='pmp_specs']/div";
        return (ArrayList<String>) driver.findElements(By.xpath(xPath)).stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public String getOrderPageUrlByPlanName(String planName) {
        for (int i = 0; i < PRODUCT_PLAN_NAMES.size(); i++) {
            if (PRODUCT_PLAN_NAMES.get(i).getText().equals(planName)) {
                return BUY_NOW_BUTTONS.get(i).getAttribute("href");
            }
        }
        return "Can't return order page URL by plan name";
    }

    @FindBy(xpath = "//div[contains(@class,'product cflex')]")
    private List<WebElement> PLAN_COLUMNS;
    private By PLAN_NAME = By.xpath(".//div[contains(text(),'Door')]");
    private By PLAN_PRICE_DOLLARS = By.xpath(".//span[contains(@class,'product-price')]");


    public BasePlanPage.Plan getSelectedPlan(String planName) {
        BasePlanPage.Plan plan = new BasePlanPage.Plan();
        plan.setPrice(0.0);
        scrollToWebElement(PLAN_COLUMNS.get(0));
        for (WebElement webElement : PLAN_COLUMNS) {
            if (planName.equalsIgnoreCase(webElement.findElement(PLAN_NAME).getText())) {
                plan.setPlanName(webElement.findElement(PLAN_NAME).getText());
                plan.setPrice(calculatePriceForPlan(webElement.findElement(PLAN_PRICE_DOLLARS)));
                LogForTest.info("Product name: " + plan.getPlanName() +
                        " Product price: " + plan.getPrice());
                return plan;
            }
        }
        return plan;
    }

    public void selectPlanByName(String planName) {
        if (PRODUCT_PLAN_NAMES.size() == 0) {
            Assert.fail("Can't return order page url for product name: " + planName + ", occur error:" +
                    "\n Product names list is empty");
        }
        for (int i = 0; i < PRODUCT_PLAN_NAMES.size(); i++) {
            if (PRODUCT_PLAN_NAMES.get(i).getText().equals(planName)) {
                click(BUY_NOW_BUTTONS.get(i), "Buy now button");
                return;
            }
        }
    }

    public void checkAndCloseCookies() {
       waitForElement(FRAME);
        try {
            WebElement closeButton = driver.findElement(By.xpath("//div[@class='pdynamicbutton']/a[1]"));
            if(closeButton.isDisplayed()) {
                click(closeButton, "Close button");
            }
        } catch (Exception e) {
            Assert.fail("Can't click close cookie cross, occur error\n" + e.getMessage());
        }
    }
}
