package contact.pages;

import base.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class ContactPage extends BasePage {

    @FindBy(xpath = "//input[@name='name']")
    private WebElement USER_NAME;
    @FindBy(xpath = "//input[@name='email']")
    private WebElement EMAIL;
    @FindBy(xpath = "//textarea[@name='message']")
    private WebElement MESSAGE_TEXT;
    @FindBy(xpath = "//button[@id='fb_submit']")
    private WebElement SUBMIT_BUTTON;
    @FindBy(xpath = "//div[@class='fb_status']")
    private WebElement ERROR_MESSAGE;
    @FindBy(xpath = "//input[@class='btn btn-close']")
    private WebElement CLOSE_BUTTON;

    public ContactPage (EventFiringWebDriver driver) {super(driver);}

    public void inputUserName(String name) { input(USER_NAME, name,
            "User name field on Contact us page");
    }

    public void inputEmail(String email) { input(EMAIL, email,
            "Email field on Contact us page");
    }

    public void inputMessage(String message) { input(MESSAGE_TEXT, message,
            "Message field on Contact us page");
    }

    public void clickSendButton() { click(SUBMIT_BUTTON, "SEND button on contact us page"); }

    public void clickCloseButton() { click(CLOSE_BUTTON, "CLOSE button on contact us page"); }

    public String getValidationError() { return ERROR_MESSAGE.getText(); }
}
