Feature: Default functionalities

  Background:
    Given I am the user of app "hello"
  
  Scenario: Run --version
    When I run app with "--version" args
    Then it should show "Hello version 0.1-SNAPSHOT"
  
  Scenario: Run --help
    When I run app with "--help" args
    Then it should at least show "-d, --debug"
    And it should at least show "-h, --help"
    And it should at least show "-i, --info"
    And it should at least show "-s, --stacktrace"
    And it should at least show "-v, --version"
    And it should at least show "echo      Echo messages"
    And it should at least show "Usage: echo [options]"
    And it should at least show "-m, --message   The message to echo"
    And it should at least show "config      Informations about configuration"
    And it should at least show "Usage: config [options]"
    And it should at least show "-f, --files"
    And it should at least show "-g, --get"
    And it should at least show "-l, --list"

  Scenario: Run config --files
    When I run app with "config --files" args
    Then output line "0" should be equal to "Configuration files:"
    And output line "1" should contain os path "/src/test/data/apps/hello/conf/hello.groovy"
    And output line "1" should contain "true"
    And output line "2" should contain os path "/hello.groovy"
    And output line "3" should contain os path "/hello.groovy"

  Scenario: Run config --list
    When I run app with "config --list" args
    Then output line "0" should be equal to "server.host=http://localhost"
    And output line "1" should be equal to "server.port=8081"

  Scenario: Run config --get server.port
    When I run app with "config --get server.port" args
    Then output line "0" should be equal to "8081"
    
  Scenario: Run config --get no args
    When I run app with "config --get" args
    Then output line "0" should contain "com.beust.jcommander.ParameterException"
    And output line "1" should be equal to "Expected a value after parameter --get"

  Scenario: Run a not valid command
    When I run app with "no-such-command" args
    Then it should exit with value "1"
    
