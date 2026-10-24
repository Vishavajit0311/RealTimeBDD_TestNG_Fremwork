package com.omnibank.automation.stepdefinitions;

import com.omnibank.automation.base.DriverManager;
import com.omnibank.automation.pages.DashboardPage;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class DashboardSteps {

    @Then("the dashboard should be displayed")
    public void the_dashboard_should_be_displayed() {
        DashboardPage dashboardPage = new DashboardPage(DriverManager.getDriver());
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Dashboard was not displayed after login");
    }
}
