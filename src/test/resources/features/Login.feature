
Feature: Login functionality for OmniBank application

  As a bank customer
  I want to log in with my credentials
  So that I can access my account dashboard

  Background:
    Given the user is on the OmniBank login page

  @smoke @regression
  Scenario Outline: Login with different sets of credentials
    When the user enters username "<username>" and password "<password>"
    And the user clicks the login button
    Then the login result should be "<expectedResult>"

    Examples:
      | username   | password   | expectedResult |
      | admin      | admin123   | Success        |
      | wronguser  | admin123   | Failure        |
      | admin      | wrongpass  | Failure        |
