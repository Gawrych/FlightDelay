package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DelayFactorCreatorImpl implements DelayFactorCreator {

    private final ResourceBundleMessageSource messageSource;

    // TODO: Create data validation
    @Override
    public DelayFactor createFactor(FactorName factorName, int value, FactorInfluence influence) {
        return DelayFactor.builder()
                .title(getMessage(factorName.name()))
                .influenceOnDelay(influence)
                .value(value)
                .build();
    }

    @Override
    public String getMessage(String title) {
        // TODO: Handle the exception
        return messageSource.getMessage(title, null, LocaleContextHolder.getLocale());
    }
}
