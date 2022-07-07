Feature: Logout user

  Scenario: Logout user
    Then Click "LogInBtn" button
    Then Input user login
    Then Input user password
    Then Click button enter
    Then Click "LogOutBtn" button
    Then Content with "Вход/Регистрация" visible