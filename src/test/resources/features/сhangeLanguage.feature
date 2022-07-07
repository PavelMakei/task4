Feature: Change language

  Scenario: Change language
    Then Click "languageBtn" button
    Then Content with "About" visible
    Then Click "languageBtn" button
    Then Content with "О нас" visible

