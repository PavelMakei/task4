Feature: Login user

  Scenario: Login user
    Then Click "LogInBtn" button
    Then Input login
    Then Input password
    Then Click button enter
    Then Content with "Выход" visible

