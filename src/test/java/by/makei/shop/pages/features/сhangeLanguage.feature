Feature: Change language

  Scenario: Change language
    When Click "languageBtn" button
    Then Content with "About" visible
    And Click "languageBtn" button
    And Content with "О нас" visible

