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
    public DelayFactor createFactor(FactorName factorName, int value, FactorInfluence influence) {
        return DelayFactor.builder()
                .title(getMessage(factorName))
                .unit(factorName.getUnit())
                .influenceOnDelay(influence)
                .value(value)
                .build();
    }

    @Override
    public String getMessage(FactorName factorName) {
        return messageSource.getMessage(factorName.name(), null, LocaleContextHolder.getLocale());
    }
}
