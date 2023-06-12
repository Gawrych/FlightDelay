package com.flightDelay.flightdelayapi.shared.config;

import com.flightDelay.flightdelayapi.shared.validator.EnumValidatorConstraint;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:properties/database.properties")
@PropertySource("classpath:properties/weatherOpenMeteoApi.properties")
@PropertySource("classpath:properties/flightPhasesLimits.properties")
@PropertySource("classpath:properties/statisticsFactors.properties")
@PropertySource("classpath:properties/weatherFactors.properties")
@PropertySource("classpath:properties/dateAndTimePatterns.properties")
@PropertySource("classpath:properties/importDataFromFile.properties")
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
