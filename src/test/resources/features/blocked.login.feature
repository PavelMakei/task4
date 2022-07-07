Feature: Login blocked
  Scenario: Login blocked
    Then Click "LogInBtn" button
    Then Input blocked login
    Then Input blocked password
    Then Click button enter
    Then Content with "Этот аккаунт заблокирован." visible