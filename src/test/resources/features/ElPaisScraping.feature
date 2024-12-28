Feature: Scraping El Pais Articles and Analyzing Translations

  Scenario: Scrape Articles from the Opinion Section and Analyze Translated Titles
    Given I visit the El Pais website
    Then I should see the website in Spanish
    When I should navigate to the Opinion section and scrape the first five articles from the Opinion section
    Then I should print the title and content of each article in Spanish
    And I should save the cover image of each article
    When I translate the titles of each article to English
    Then I should print the translated headers
    When I analyze the translated headers
    Then I should print the repeated words along with their count