Feature: Override of main command

  Scenario: Run --wersion
    Given I am the user of app 'over'
    When I run app with "--wersion" args
    Then it should show "OverApp Wersion 3.2.1"

  Scenario: Run info level
    Given I am the user of app 'over'
    When I run app with "--hinfo" args
    Then output line '0' should be equal to 'args_info=true'
    #And output line '1' should be equal to 'over_output_'

