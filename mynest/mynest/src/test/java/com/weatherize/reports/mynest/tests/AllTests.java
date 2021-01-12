package com.weatherize.reports.mynest.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestMyNestApplicationMvcTest.class, TestMyNestApplicationMockitoTest.class,TestMyNestApplicationRestassuredTest.class,TestMyNestApplicationCucumberTest.class})
public class AllTests {

}
