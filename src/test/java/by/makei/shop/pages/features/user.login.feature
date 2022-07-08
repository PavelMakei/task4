Feature: Login user

  Scenario: Login user
    When Click "LogInBtn" button
    And Input user login
    And Input user password
    And Click button enter
    Then Content with "Выход" visible


