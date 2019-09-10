package base.pages;

import instances.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import utils.logs.LogForTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BasePaymentPage extends BasePage {

    @FindBy(xpath = "//*[@class='product']")
    private WebElement FIRST_PRODUCT_SECTION;
    @FindBy(xpath = "//*[@class='product']")
    private List<WebElement> PRODUCT_SECTION_LIST;
    @FindBy(xpath = "//span[@class='payment-due__price']")
    private WebElement TOTAL_PRICE;

    public BasePaymentPage(EventFiringWebDriver driver) {super(driver);}

    //Init product details
    private static final By PRODUCT_SECTION = By.xpath(".//*[@class='product__description']");
    private static final By FULL_PRODUCT_NAME = By.xpath(".//span[@class='product__description__name order-summary__emphasis']");
    private static final By SELECTED_QUANTITY = By.xpath(".//following::td[@class='product__quantity visually-hidden']");

    public ArrayList<Product> getShoppingCartItemsDescription() {
        waitForElement(FIRST_PRODUCT_SECTION, "Any Item in Cart");
        ArrayList<Product> shoppingCartItems = new ArrayList<>();
        LogForTest.info("PAYMENT PAGE: Found in shopping cart " + PRODUCT_SECTION_LIST.size() + " product(s)");
        for (WebElement productSection : PRODUCT_SECTION_LIST) {
            Product shoppingCartItem = new Product();

            final WebElement currentProduct = productSection.findElement(PRODUCT_SECTION);
            String productName = getProductName(currentProduct);
            shoppingCartItem.setName(productName);
            shoppingCartItem.setQuantity(getSelectedQuantity(currentProduct));
            shoppingCartItems.add(shoppingCartItem);
        }
        //noinspection unchecked
        Collections.sort(shoppingCartItems);
        return shoppingCartItems;
    }

    @Override
    protected String getProductName(WebElement webElement) {
        final List<WebElement> list = webElement.findElements(FULL_PRODUCT_NAME);
        if (list.size() > 1) {
            return list.get(0).getText() + " - " + list.get(1).getText();
        }
        return list.get(0).getText();
    }

    @Override
    protected String getSelectedQuantity(WebElement webElement) {
        if (!webElement.findElement(By.xpath(".//following::td[@class='product__quantity visually-hidden']"))
                .getText().isEmpty()) {
            return webElement.findElement(SELECTED_QUANTITY).getAttribute("value");
        } else return "NOT FOUND";
    }

    public double calculatePrice() {
        scrollToWebElement(TOTAL_PRICE);
        LogForTest.info("Total Price: " + getPriceFromWebElement(TOTAL_PRICE));
        return getPriceFromWebElement(TOTAL_PRICE);
    }
}
