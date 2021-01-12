package com.weatherize.reports.mynest.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
public class TestMyNestApplicationMvcTest {

	@Autowired
	private MockMvc mockMvc;

	public TestMyNestApplicationMvcTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Test
	public void TempDataLoadTest() throws Exception {
		this.mockMvc.perform(get("/api/thermostats")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.values").exists());
	}

	@Test
	public void SensorDataLoadTest() throws Exception {
		this.mockMvc.perform(get("/api/sensordata")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.values").exists());
	}

}
