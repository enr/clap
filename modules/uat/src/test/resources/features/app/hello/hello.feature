Feature: Default functionalities

  Scenario: Run --version
    Given I am the user of app 'hello'
    When I run app with "--version" args
    Then it should show "Hello version 0.1-SNAPSHOT"

  Scenario: Run config --files
    Given I am the user of app 'hello'
    When I run app with "config --files" args
    Then output line '0' should be equal to 'Configuration files:'
    And output line '1' should contain os path '/src/test/data/apps/hello/conf/hello.groovy'
    And output line '1' should contain 'true'
    And output line '2' should contain os path '/hello.groovy'
    And output line '3' should contain os path '/hello.groovy'

  Scenario: Run config --list
    Given I am the user of app 'hello'
    When I run app with "config --list" args
    Then output line '0' should be equal to 'server.host=http://localhost'
    Then output line '1' should be equal to 'server.port=8081'

  Scenario: Run config --get server.port
    Given I am the user of app 'hello'
    When I run app with "config --get server.port" args
    Then output line '0' should be equal to '8081'
    
  Scenario: Run config --get no args
    Given I am the user of app 'hello'
    When I run app with "config --get" args
    Then output line '0' should contain 'com.beust.jcommander.ParameterException'
    And output line '1' should be equal to 'Expected a value after parameter --get'

  Scenario: Run a not valid command
    Given I am the user of app 'hello'
    When I run app with "no-such-command" args
    Then it should exit with value "1"