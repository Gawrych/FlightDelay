package com.flightDelay.flightdelayapi.shared;

import org.springframework.http.ResponseEntity;

public interface UpdateFromJson {

    ResponseEntity<String> updateFromJson(String newDataInJsonString);
}
