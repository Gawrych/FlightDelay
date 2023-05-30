package com.flightDelay.flightdelayapi.weatherFactors.creator;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.WeatherFactorName;
import com.flightDelay.flightdelayapi.weatherFactors.model.WeatherFactor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeatherFactorCreatorImpl implements WeatherFactorCreator {

    private final ResourceBundleMessageSource messageSource;

    @Override
    public WeatherFactor createFactor(WeatherFactorName weatherFactorName, int value, FactorInfluence influence) {
        return WeatherFactor.builder()
                .id(weatherFactorName)
                .title(getMessage(weatherFactorName))
                .unit(weatherFactorName.getUnit())
                .influenceOnDelay(influence)
                .value(value)
                .build();
    }

    private String getMessage(WeatherFactorName weatherFactorName) {
        return messageSource.getMessage(weatherFactorName.name(), null, LocaleContextHolder.getLocale());
    }
}
