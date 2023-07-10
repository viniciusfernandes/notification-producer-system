Feature: Advertisement Notification Creation

  Scenario: Creating an advertisement notification successfully
    Given the notification request '/requests/advertisement-notification.json'
    When I 'POST' this request to the the advertisement notification endpoint
    Then I get 200 as response status
    And I have one new notification record in my data base
