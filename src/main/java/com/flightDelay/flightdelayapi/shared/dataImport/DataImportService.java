package com.flightDelay.flightdelayapi.shared.dataImport;

import java.util.List;

public interface DataImportService {

    List<?> importFromFile(UpdateFromJson entityAbleToBeUpdatedByJson, String scriptName);
}
