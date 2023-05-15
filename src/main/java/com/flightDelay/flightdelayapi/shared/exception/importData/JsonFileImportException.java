package com.flightDelay.flightdelayapi.shared.exception.importData;

import com.flightDelay.flightdelayapi.shared.exception.CustomRuntimeException;

public class JsonFileImportException extends CustomRuntimeException {

    public JsonFileImportException(String message, Object[] parameters) {
        super(message, parameters);
    }
}
