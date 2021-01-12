package com.weatherize.reports.mynest.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weatherize.reports.mynest.MynestdataApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MynestdataApplication.class)
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}