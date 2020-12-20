package com.weatherize.reports.mynest.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

import org.springframework.web.bind.annotation.CrossOrigin;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@CrossOrigin
public @interface CrossOriginsList {

    public String[] crossOrigins() default  {

            "http://localhost:3000",
            "http://localhost:8085"
    };
}