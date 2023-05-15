package com.flightDelay.flightdelayapi.shared.exception.importData;

public class JsonFileDataConverterUnexpectedExitCodeException extends JsonFileImportException {

    public JsonFileDataConverterUnexpectedExitCodeException(String converterName, Integer icaoCode) {
        super("error.message.jsonFileDataConverterUnexpectedExitCodeException", new Object[]{converterName, icaoCode});
    }
}
