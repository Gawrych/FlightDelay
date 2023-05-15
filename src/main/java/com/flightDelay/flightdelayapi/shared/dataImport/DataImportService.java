package com.flightDelay.flightdelayapi.shared.dataImport;

import com.flightDelay.flightdelayapi.shared.UpdateFromJson;
import org.springframework.http.ResponseEntity;

public interface DataImportService {

    String importFromFile(UpdateFromJson entityAbleToBeUpdatedByJson, String scriptName);
}
