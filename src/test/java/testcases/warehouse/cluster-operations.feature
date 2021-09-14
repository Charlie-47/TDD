Feature: Cluster Operations

  Scenario:
    Given url 'http://www.baidu.com'
    When method get
    Then status 200