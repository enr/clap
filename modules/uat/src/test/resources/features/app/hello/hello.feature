Feature: Default functionalities

  Scenario: Run --version
    Given I am the user of app 'hello'
    When I run app with "--version" args
    Then it should show "Hello version 0.1-SNAPSHOT"

  Scenario: Run --configurations
    Given I am the user of app 'hello'
    When I run app with "--configurations" args
    Then output line '0' should show 'Configuration files:'
    And output line '1' should show os path '/src/test/data/apps/hello/conf/hello.groovy'
    And output line '1' should show 'true'
    And output line '2' should show os path '/hello.groovy'
    And output line '3' should show os path '/hello.groovy'
