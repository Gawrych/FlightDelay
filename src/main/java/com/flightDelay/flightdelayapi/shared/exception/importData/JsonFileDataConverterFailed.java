package com.flightDelay.flightdelayapi.shared.exception.importData;

public class JsonFileDataConverterFailed extends JsonFileImportException {

    public JsonFileDataConverterFailed(String converterName) {
        super("error.message.jsonFileDataConverterFailed", new Object[]{converterName});
    }
}
