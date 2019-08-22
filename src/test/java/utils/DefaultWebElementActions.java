package utils;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

public class DefaultWebElementActions {

    /**
     * @param element            WebElement to click on
     * @param elementDescription element description (will be used if occur any exception)
     */
    protected void click(WebElement element, String elementDescription) {
        try {
            element.click();
        } catch (Exception e) {
            Assert.fail("Can't click on ['" + elementDescription + "'], occur error\n" + e.getMessage());
        }
    }

    /**
     * @param webElements        List of WebElements, any displayed element from this list will be clicked
     *                           NOTICE:Do not use this method in case if more than one elements are displayed
     * @param elementDescription element description (will be used if occur any exception)
     */
    protected void click(List<WebElement> webElements, String elementDescription) {
        WebElement displayedWebElement = webElements.stream().filter(WebElement::isDisplayed).findAny().orElse(null);
        if (null != displayedWebElement) {
            click(displayedWebElement, elementDescription);
        } else {
            Assert.fail("Can't find displayed " + elementDescription);
        }
    }

    /**
     * @param element            WebElement to input
     * @param toInput            text to be input
     * @param elementDescription element description (will be used if occur any exception)
     */
    protected void input(WebElement element, String toInput, String elementDescription) {
        try {
            element.clear();
            element.sendKeys(toInput);
        } catch (Exception e) {
            Assert.fail("Can't input \"" + toInput + "\" to [" + elementDescription + "], occur error\n" + e.getMessage());
        }
    }

    /**
     * @param webElements        List of WebElements, text (toInput) will be input to any displayed element from this list
     *                           NOTICE:Do not use this method in case if more than one elements are displayed
     * @param toInput            text to be input
     * @param elementDescription element description (will be used if occur any exception)
     */
    protected void input(List<WebElement> webElements, String toInput, String elementDescription) {
        WebElement displayedWebElement = webElements.stream().filter(WebElement::isDisplayed).findAny().orElse(null);
        if (null != displayedWebElement) {
            input(displayedWebElement, toInput, elementDescription);
        } else {
            Assert.fail("Can't find displayed " + elementDescription);
        }
    }

    protected void selectFromDropdown(List<WebElement> dropdown, String visibleText, String elementDescription) {
        try {
            dropdown.stream().filter(webElement -> webElement.getText().equalsIgnoreCase(visibleText)).
                    forEach(webElement -> click(webElement, visibleText));
        } catch (Exception e) {
            Assert.fail("Can't select [" + visibleText + "] from " + elementDescription + " dropdown, occur error\n" + e.getMessage());
        }
    }
}
