package base.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.DefaultWebElementActions;
import utils.logs.LogForTest;

import java.util.List;

public class BasePage extends DefaultWebElementActions {
    protected double totalPrice;
    protected static EventFiringWebDriver driver;

    protected BasePage(EventFiringWebDriver driver)  {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public double getTotalPrice() {
        return this.totalPrice;
    }

    public String getPageDescription() {
        return "Base page";
    }

    protected void scrollToWebElement(WebElement element) {
        try {
            JavascriptExecutor js = driver;
            js.executeScript("scrollTo(0, " + (element.getLocation()
                    .getY() - driver.manage().window().getSize().getHeight() / 2) + ")"); //Super scroll
        } catch (Exception e) {
            LogForTest.LOGGER.warn("Can't scroll to WebElement", e);
        }
    }

    public static double getPriceFromWebElement(WebElement element) {
        String priceString = element.getText();
        if (priceString.toLowerCase().equals("free")) {
            return 0.0;
        }
        String currency = "£";
        double priceDouble = -1;
        priceString = priceString.replace("/mo", "").replace("/yr", "")
                .replace(" / monthly", "").replace("Most Popular", "")
                .replace(currency, "").trim().replaceAll(",", "");
        try {
            priceDouble = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            LogForTest.error("Exception while parsing WebElement \"" + element.getText() + "\".");
        }
        return priceDouble;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    protected static void waitForElement(WebElement SomeLocatorByXpath) {
        WebDriverWait waitForOne = new WebDriverWait(driver, 20);
        waitForOne.until(ExpectedConditions.visibilityOf(SomeLocatorByXpath));
    }

    public void waitForElement(WebElement element, String elementDescription) {
        try {
            new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            Assert.fail("Timeout Exception: Can't wait for " + elementDescription + "\n" + e.getMessage());
        }
    }

    /**
     * Wait for URL contains text1 or text2 for 15 second, if texts will be not present in URl test will be failed
     *
     * @param text1 some text to be present in URL
     * @param text2 some text to be present in URL
     */
    public void waitURLContainsText(String text1, String text2) {
        try {
            new WebDriverWait(driver, 15)
                    .until(ExpectedConditions.or(ExpectedConditions.urlContains(text1), ExpectedConditions.urlContains(text2)));
        } catch (TimeoutException e) {
            Assert.fail("Exception while wait text \"" + text1 + " and " + text2 + "\" in url\n" + e.getMessage());
        }
    }

    private static final By FULL_PRODUCT_NAME = By.xpath(".//span[@class='product__description__name order-summary__emphasis']");
    private static final By SELECTED_TERM = By.xpath(".//input[@class='cart__qty-input']");

    protected String getProductName(WebElement webElement) {
        final List<WebElement> list = webElement.findElements(FULL_PRODUCT_NAME);
        if (list.size() > 1) {
            return list.get(0).getText() + " - " + list.get(1).getText();
        }
        return list.get(0).getText();
    }

    protected String getSelectedQuantity(WebElement webElement) {
        if (!webElement.findElement(By.xpath(".//input[@class='cart__qty-input']"))
                .getText().isEmpty()) {
            return webElement.findElement(SELECTED_TERM).getAttribute("value");
        } else return "NOT FOUND";
    }

    protected double calculatePriceForPlan(WebElement dollars){
        return getPriceFromWebElement(dollars);
    }
}
