package com.flightDelay.flightdelayapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication
public class FlightDelayApiApplication {


	public static void main(String[] args) {
		SpringApplication.run(FlightDelayApiApplication.class, args);
	}

}
