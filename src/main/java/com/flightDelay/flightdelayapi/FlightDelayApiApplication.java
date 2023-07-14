package com.flightDelay.flightdelayapi;

import com.flightDelay.flightdelayapi.weatherFactors.properties.LandingLimitsProperties;
import com.flightDelay.flightdelayapi.weatherFactors.properties.TakeoffLimitsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@EnableConfigurationProperties({LandingLimitsProperties.class, TakeoffLimitsProperties.class})
public class FlightDelayApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightDelayApiApplication.class, args);
	}
}