Feature: calling tests of virtual warehouse

  Scenario: test baidu
    Given url 'http://www.baidu.com'
    When method get
    Then status 200