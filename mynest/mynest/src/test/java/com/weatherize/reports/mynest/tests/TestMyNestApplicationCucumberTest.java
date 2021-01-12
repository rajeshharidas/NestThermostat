package com.weatherize.reports.mynest.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.springframework.http.HttpStatus;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TestMyNestApplicationCucumberTest extends CucumberTests {

	@Given("^the client calls /thermostats$")
	public void the_client_issues_GET_thermostats() throws Throwable {

		executeGet("http://localhost:8190/mynest/api/thermostats");

	}
	
	@Given("^the client calls /sensordata$")
	public void the_client_issues_GET_sensordata() throws Throwable {

		executeGet("http://localhost:8190/mynest/api/sensordata");

	}

	@When("^the client receives status code of (\\d+)$")
	public void the_client_receives_status_code_of(int statusCode) throws Throwable {
		final HttpStatus currentStatusCode = latestResponse.getTheResponse().getStatusCode();
		assertEquals(currentStatusCode.value(), statusCode);
	}

	@Then("^the client receives all the values")
	public void the_client_receives_all_the_values() {
		assertTrue(latestResponse.getBody().contains("values"));
	}
}