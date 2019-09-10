package products.doorbells.pages;

import base.pages.BaseOrderPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import utils.logs.LogForTest;

import java.util.List;

public class DoorBellsOrderPage extends BaseOrderPage {

    @FindBy(xpath = "//div[@class='qty_btn up']")
    private WebElement QUANTITY_BUTTON_UP;
    @FindBy(xpath = "(//span[contains(@class,'product-price__price')]/span)[2]")
    private WebElement PRODUCT_PRICE;
    @FindBy(xpath = "(//h1)[2]")
    private WebElement PRODUCT_NAME;
    @FindBy(xpath = "//button[contains(@id,'AddToCart')]")
    private WebElement ADD_TO_CART_BUTTON;
    @FindBy(xpath = "//a[@class='cart_link']")
    private WebElement SHOPPING_CART_BUTTON;
    @FindBy(xpath = "//span[@class='qty']")
    private WebElement PRODUCT_QUANTITY;

    public DoorBellsOrderPage (EventFiringWebDriver driver) {super(driver);}

    public void changeProductQuantity() {
        waitForElement(QUANTITY_BUTTON_UP);
        click(QUANTITY_BUTTON_UP, "Quantity button up");
        click(QUANTITY_BUTTON_UP, "Quantity button up");
    }

    public double calculatePlanPrice() {
        double planPrice = getPriceFromWebElement(PRODUCT_PRICE);
        return planPrice * 3;
    }

    public static double getPriceFromWebElement(WebElement element) {
        String priceString = element.getText();
        if (priceString.toLowerCase().equals("free")) {
            return 0.0;
        }
        double priceDouble = -1;
        priceString = priceString.replace("£", "");
        try {
            priceDouble = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            LogForTest.error("Exception while parsing WebElement \"" + element.getText() + "\".");
        }
        return priceDouble;
    }

    public String getProductName() {
        try {
            return PRODUCT_NAME.getText();
        } catch (Exception e) {
            Assert.fail("Can't return product name, occur error:\n" + e.getMessage());
            return "";
        }
    }

    public String getSelectedProductQuantity() {
        waitForElement(PRODUCT_QUANTITY);
        return PRODUCT_QUANTITY.getText();
    }

    @FindBy(xpath = "//div[contains(@class,'product_details__column')]")
    private List<WebElement> PLAN_COLUMNS;
    private By PLAN_NAME = By.xpath(".//h1[contains(text(),'Door')]");
    private By PLAN_PRICE_DOLLARS = By.xpath(".//span[contains(@class,'product-price')]");

    public BaseOrderPage.Plan getSelectedPlan(String planName) {
        BaseOrderPage.Plan plan = new BaseOrderPage.Plan();
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

    public void clickAddToCartButton() {click(ADD_TO_CART_BUTTON, "Add to cart button on order page");}

    public void clickShoppingCartButton() {click(SHOPPING_CART_BUTTON, "Shopping cart button");}
}
