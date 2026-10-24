package com.omnibank.automation.stepdefinitions;

import com.omnibank.automation.base.DriverManager;
import com.omnibank.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginSteps {

    private LoginPage loginPage;
    private boolean isLoginSuccessful;

    @Given("the user is on the OmniBank login page")
    public void the_user_is_on_the_login_page() {
        loginPage = new LoginPage(DriverManager.getDriver());
    }

    @When("the user enters username {string} and password {string}")
    public void the_user_enters_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @When("the user clicks the login button")
    public void the_user_clicks_the_login_button() {
        loginPage.clickLogin();
        isLoginSuccessful = loginPage.isLoginSuccessful();
    }

    @Then("the login result should be {string}")
    public void the_login_result_should_be(String expectedResult) {
        if (expectedResult.equalsIgnoreCase("Success")) {
            Assert.assertTrue(isLoginSuccessful, "Expected login to succeed but it failed");
        } else {
            Assert.assertFalse(isLoginSuccessful, "Expected login to fail but it succeeded");
        }
    }
}
