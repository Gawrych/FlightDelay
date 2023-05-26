package com.flightDelay.flightdelayapi.shared.config;

import com.flightDelay.flightdelayapi.shared.validator.EnumValidatorConstraint;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:api/weatherApi.properties")
@PropertySource("classpath:flightPhasesLimits.properties")
@PropertySource("classpath:weatherSettings.properties")
@PropertySource("classpath:dateAndTimePatterns.properties")
@PropertySource("classpath:importDataFromFile.properties")
public class AppConfig {

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasenames("lang/factors", "lang/error_messages", "lang/messages");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setCacheSeconds(600);
        return resourceBundleMessageSource;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public EnumValidatorConstraint enumValidator() {
        return new EnumValidatorConstraint();
    }
}