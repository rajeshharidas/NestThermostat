Feature: the thermostat data can be retrieved
  Scenario: client makes call to GET /thermostats
  	Given the client calls /thermostats
    When the client receives status code of 200
	Then the client receives all the values
