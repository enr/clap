Feature: Command allowing noop switch

  Background:
    Given I am the user of app "noop"

  Scenario: Run echo --message hellonoop
    When I run app with "echo --message hellonoop" args
    Then output line "0" should be equal to "hellonoop"

  Scenario: Run echo --noop --message hellonoop
    When I run app with "echo --noop --message hellonoop" args
    Then output line "0" should be equal to "Explanation for command echo"
