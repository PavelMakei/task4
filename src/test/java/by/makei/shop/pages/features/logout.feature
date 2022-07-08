Feature: Logout user

  Scenario: Logout user
    Given Click "LogInBtn" button
    And Input user login
    And Input user password
    And Click button enter
    When Click "LogOutBtn" button
    Then Content with "Вход/Регистрация" visible