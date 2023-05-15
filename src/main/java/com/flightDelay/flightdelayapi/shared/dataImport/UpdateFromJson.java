package com.flightDelay.flightdelayapi.shared.dataImport;

import org.springframework.http.ResponseEntity;

public interface UpdateFromJson {

    String updateFromJson(String newDataInJsonString);
}
