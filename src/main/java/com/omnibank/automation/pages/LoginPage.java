package com.omnibank.automation.pages;

import com.omnibank.automation.utilities.Constants;
import com.omnibank.automation.utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    private WebDriver driver;

    @FindBy(id = "input-email")
    private WebElement usernameField;

    @FindBy(id = "input-password")
    private WebElement passwordField;

    @FindBy(css = "input[value='Login']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterUsername(String username) {
        WaitUtils.waitForElement(driver, By.id("input-email"), Constants.EXPLICIT_WAIT);
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        WaitUtils.waitForClickable(driver, By.cssSelector("input[value='Login']"), Constants.EXPLICIT_WAIT);
        loginButton.click();
    }

    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("account/account");
    }
}
