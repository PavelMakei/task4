Feature: Login blocked
  Scenario: Login blocked
    When Click "LogInBtn" button
    And Input blocked login
    And Input blocked password
    And Click button enter
    Then Content with "Этот аккаунт заблокирован." visible