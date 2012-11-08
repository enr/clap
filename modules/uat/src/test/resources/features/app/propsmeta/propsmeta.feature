Feature: Properties backed meta

  Background:
    Given I am the user of app "propsmeta"
  
  Scenario: Run --version
    When I run app with "--version" args
    Then it should show "Clap User Acceptance Test Application version 1.2-SNAPSHOT"
  
