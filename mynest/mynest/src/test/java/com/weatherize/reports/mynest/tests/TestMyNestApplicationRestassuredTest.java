package com.weatherize.reports.mynest.tests;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import com.weatherize.reports.mynest.model.Cycle;
import com.weatherize.reports.mynest.model.NestResponse;
import com.weatherize.reports.mynest.model.SensorData;
import com.weatherize.reports.mynest.model.Thermostat;
import com.weatherize.reports.mynest.service.SensorDataService;
import com.weatherize.reports.mynest.service.ThermostatService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static io.restassured.RestAssured.get;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMyNestApplicationRestassuredTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private String uri;


	@MockBean
	ThermostatService thermostatService;
	
	@Mock
	SensorDataService sensorDataService;

	@PostConstruct
	public void init() {
		uri = "http://localhost:8190";
	}
	
	@Before
	public void initialiseRestAssuredMockMvcWebApplicationContext() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
	}
	
	@Test
	public void MyThermostatTest() {
		
		List<Thermostat> data = new ArrayList<Thermostat>();
		List<Cycle> cycles = new ArrayList<Cycle>();

		Cycle cycle = new Cycle("heating cycle from 12:00 AM to 11:58 AM", "43133s", true, false, false,
				"2020-12-01T18:00:00Z", "2020-12-02T05:58:53Z", "Morning Heating");
		cycles.add(cycle);

		data.add(new Thermostat("5fe790458fda0b5574c5eba6", "2020-12-13T18:00:00", "2020-12-14T18:00:00", cycles));

		cycle = new Cycle("heating cycle from 12:00 AM to 2:52 AM", "10367s", true, false, false,
				"2020-12-13T18:00:00Z", "2020-12-13T20:52:47Z", "Afternoon Heating");
		cycles.add(cycle);

		data.add(new Thermostat("5fe760458fda0b5574d5eba6", "2020-12-01T18:00:00", "2020-12-02T18:00:00", cycles));

		when(thermostatService.GetAllThermostatEntries(0, 20))
				.thenReturn(new NestResponse<Thermostat>(data, 5, 1, 15, 0));
		
		get(uri + "/mynest/api/thermostats").then()
		.assertThat()
        .statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void MySensordataTest() {
		
		List<SensorData> data = new ArrayList<SensorData>();

		data.add(new SensorData("5fe790458fda0b5574c5eca6", "2020-12-26", "14:00", "21.67", "34.01"));
		data.add(new SensorData("5fe710458fda0b4574c5eba6", "2020-12-26", "13:45", "21.41", "32.23"));
		data.add(new SensorData("5fe790458fda3b5574c5eba6", "2020-12-26", "13:30", "21.53", "35.09"));
		
		when(sensorDataService.GetAllSensorData(0, 20))
		.thenReturn(new NestResponse<SensorData>(data, 5, 1, 15, 0));
		
		NestResponse<SensorData> entries = sensorDataService.GetAllSensorData(0, 20);
		
		get(uri + "/mynest/api/sensordata").then()
		.assertThat()
        .statusCode(HttpStatus.SC_OK);
	}

}
