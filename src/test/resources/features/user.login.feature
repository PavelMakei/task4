Feature: Login user

  Scenario: Login user
    Then Click "LogInBtn" button
    Then Input user login
    Then Input user password
    Then Click button enter
    Then Content with "Выход" visible


