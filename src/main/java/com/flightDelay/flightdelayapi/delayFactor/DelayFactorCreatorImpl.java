package com.flightDelay.flightdelayapi.delayFactor;

import com.flightDelay.flightdelayapi.shared.enums.FactorInfluence;
import com.flightDelay.flightdelayapi.weatherFactors.enums.FactorName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DelayFactorCreatorImpl implements DelayFactorCreator {

    private final ResourceBundleMessageSource messageSource;

    @Override
    public DelayFactor createFactor(FactorName factorName, int value, String unit, FactorInfluence influence) {
        return DelayFactor.builder()
                .title(getMessage(factorName.name()))
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
