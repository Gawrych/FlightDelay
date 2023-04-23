package com.flightDelay.flightdelayapi.DelayFactor;

import com.flightDelay.flightdelayapi.shared.FactorInfluence;
import com.flightDelay.flightdelayapi.shared.FactorName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

public interface DelayFactorCreator {

    DelayFactor createFactor(FactorName factorName, int value, FactorInfluence influence);

    String getMessage(String title);
}
