Feature: Command allowing noop switch

  Scenario: Run echo --message hellonoop
    Given I am the user of app 'noop'
    When I run app with "echo --message hellonoop" args
    Then output line '0' should be equal to 'hellonoop'

  Scenario: Run echo --noop --message hellonoop
    Given I am the user of app 'noop'
    When I run app with "echo --noop --message hellonoop" args
    Then output line '0' should be equal to 'echo_explain'
