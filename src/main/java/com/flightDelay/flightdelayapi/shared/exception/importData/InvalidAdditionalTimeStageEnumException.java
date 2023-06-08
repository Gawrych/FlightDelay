package com.flightDelay.flightdelayapi.shared.exception.importData;

import com.flightDelay.flightdelayapi.shared.exception.request.RequestValidationException;

public class InvalidAdditionalTimeStageEnumException extends RequestValidationException {

    public InvalidAdditionalTimeStageEnumException(String phaseName) {
        super("error.message.invalidAdditionalTimeStage", new Object[]{phaseName});
    }
}
