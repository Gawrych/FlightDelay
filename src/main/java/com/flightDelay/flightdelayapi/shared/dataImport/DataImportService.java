package com.flightDelay.flightdelayapi.shared.dataImport;

public interface DataImportService {

    String importFromFile(UpdateFromJson entityAbleToBeUpdatedByJson, String scriptName);
}
