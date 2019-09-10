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

public class BaseShoppingCartPage extends BasePage {

    @FindBy(xpath = "//*[@class='cart__row border-bottom line1 cart-flex border-top']")
    private WebElement FIRST_PRODUCT_SECTION;
    @FindBy(xpath = "//*[@class='cart__row border-bottom line1 cart-flex border-top']")
    private List<WebElement> PRODUCT_SECTION_LIST;
    @FindBy(xpath = "//span[@class='wh-original-cart-total']")
    private WebElement TOTAL_PRICE;
    @FindBy(xpath = "//input[@name='checkout']")
    private WebElement CHECK_OUT_BUTTON;
    @FindBy(xpath = "//a[@class='btn btn--small btn--secondary cart__remove']")
    private WebElement REMOVE_BUTTON;

    public BaseShoppingCartPage (EventFiringWebDriver driver) {super(driver);}

    //Init product details
    private static final By PRODUCT_SECTION = By.xpath(".//*[@class='cart__meta small--text-left cart-flex-item']");
    private static final By FULL_PRODUCT_NAME = By.xpath(".//div[@class='list-view-item__title']");
    private static final By SELECTED_QUANTITY = By.xpath(".//following::input[@class='cart__qty-input']");

    public ArrayList<Product> getShoppingCartItemsDescription() {
        waitForElement(FIRST_PRODUCT_SECTION, "Any Item in Cart");
        ArrayList<Product> shoppingCartItems = new ArrayList<>();
        LogForTest.info("SHOPPING CART PAGE: Found in shopping cart " + PRODUCT_SECTION_LIST.size() + " product(s)");
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
        return list.get(0).getText();
    }

    @Override
    protected String getSelectedQuantity(WebElement webElement) {
        if (!webElement.findElement(By.xpath(".//following::input[@class='cart__qty-input']"))
                .getText().isEmpty()) {
            return webElement.findElement(SELECTED_QUANTITY).getAttribute("value");
        } else return "NOT FOUND";
    }

    public double calculatePrice() {
        waitForElement(TOTAL_PRICE, "TOTAL PRICE Element");
        scrollToWebElement(TOTAL_PRICE);
        LogForTest.info("Total Price: " + getPriceFromWebElement(TOTAL_PRICE));
        return getPriceFromWebElement(TOTAL_PRICE);
    }

    public void clickCheckOutButton() {click(CHECK_OUT_BUTTON, "Check out button on shopping cart page");}

    public void clickRemoveButton() {click(REMOVE_BUTTON, "Remove button");}
}
