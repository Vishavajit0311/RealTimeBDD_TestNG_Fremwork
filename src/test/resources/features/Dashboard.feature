Feature: Dashboard verification after login

  As a logged-in bank customer
  I want to see my dashboard immediately after logging in
  So that I can confirm my session started correctly

  @smoke
  Scenario: Verify dashboard loads after successful login
    Given the user is on the OmniBank login page
    When the user enters username "admin" and password "admin123"
    And the user clicks the login button
    Then the dashboard should be displayed
