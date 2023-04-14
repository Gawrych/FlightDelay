package com.flightDelay.flightdelayapi.dataImport;

import com.flightDelay.flightdelayapi.shared.UpdateFromJson;
import org.springframework.http.ResponseEntity;

public interface DataImportService {

    ResponseEntity<String> importFromFile(UpdateFromJson entityAbleToBeUpdatedByJson, String scriptName);
}
