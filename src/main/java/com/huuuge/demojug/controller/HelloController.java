package com.huuuge.demojug.controller;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;

import javax.validation.constraints.Size;

@Controller("/greeting")
public class HelloController {
    private static final int MAX_CITY_LENGTH = 15;
    private static final int MIN_CITY_LENGTH = 3;
    @Value("${app.returned-string}")
    private String someString;

    @Get
    public String greeting() {
        return someString;
    }

    @Get("/{city}")
    public String greetingForCity(@Size(min = MIN_CITY_LENGTH, max = MAX_CITY_LENGTH, message = "City length must be between 3-15") @PathVariable String city) {
        return "Hello " + city + "!";
    }

}
