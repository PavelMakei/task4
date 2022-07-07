Feature: Deposit money
  Scenario: Deposit money
    Then Click "LogInBtn" button
    Then Input user login
    Then Input user password
    Then Click button enter
    Then Choose option ""
    Then Content with "Этот аккаунт заблокирован." visible
