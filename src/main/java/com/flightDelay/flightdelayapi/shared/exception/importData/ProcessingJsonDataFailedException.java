package com.flightDelay.flightdelayapi.shared.exception.importData;

public class ProcessingJsonDataFailedException extends JsonFileImportException {

    public ProcessingJsonDataFailedException(String className) {
        super("error.message.processingJsonDataFailed", new Object[]{className});
    }
}
