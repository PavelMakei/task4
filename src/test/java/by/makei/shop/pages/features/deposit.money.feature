Feature: Deposit money

  Scenario: Deposit money
    Given Click "LogInBtn" button
    Given Input user login
    Given Input user password
    Given Click button enter
    Then Choose "navbarDropdown" dropdown list "depositMoneyOption" option
    And Save current amount
    And Input card number
    And Input card expiration date
    And Input card CVC
    And Input Name on card
    And Input amount
    And Click deposit button
    And Content with "Выход" visible
    And Choose "navbarDropdown" dropdown list "depositMoneyOption" option
    And Content increased amount visible
