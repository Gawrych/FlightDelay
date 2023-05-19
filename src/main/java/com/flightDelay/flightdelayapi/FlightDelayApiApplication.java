package com.flightDelay.flightdelayapi;

import com.flightDelay.flightdelayapi.weatherFactors.properties.LandingLimitsProperties;
import com.flightDelay.flightdelayapi.weatherFactors.properties.TakeoffLimitsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({LandingLimitsProperties.class, TakeoffLimitsProperties.class})
public class FlightDelayApiApplication {
	// TODO: Create slf4j custom log format and set target

	public static void main(String[] args) {
		SpringApplication.run(FlightDelayApiApplication.class, args);
	}
}
