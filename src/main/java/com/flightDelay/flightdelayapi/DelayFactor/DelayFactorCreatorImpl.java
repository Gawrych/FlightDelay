package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.WeatherFactor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DelayFactorCreatorImpl implements DelayFactorCreator {

    private final ResourceBundleMessageSource messageSource;

    @Override
    public DelayFactor createFactor(WeatherFactor weatherFactor, int value, String unit, FactorInfluence influence) {
        return DelayFactor.builder()
                .title(getMessage(weatherFactor.name()))
                .influenceOnDelay(influence)
                .value(value)
                .unit(unit)
                .build();
    }
    // TODO: Create data validation

    @Override
    public String getMessage(String title) {
        // TODO: Handle the exception
        return messageSource.getMessage(title, null, LocaleContextHolder.getLocale());
    }
}
