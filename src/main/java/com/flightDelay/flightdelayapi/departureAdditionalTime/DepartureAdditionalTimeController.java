package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/departures")
public class DepartureAdditionalTimeController {

    @Value("${import.departureAdditionalTimeConverterName}")
    private String departureAdditionalTimeConverterName;

    private final DepartureAdditionalTimeServiceImpl departureAdditionalTimeService;

    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file")
    public ResponseEntity<List<?>> updateFromFile() {
        List<?> addedEntities = dataImportService.importFromFile(departureAdditionalTimeService, departureAdditionalTimeConverterName);
        return new ResponseEntity<>(addedEntities, HttpStatus.CREATED);
    }

    @PutMapping("/json")
    public ResponseEntity<List<?>> update(@RequestBody String dataInJson) {
        List<?> addedEntities = departureAdditionalTimeService.updateFromJson(dataInJson);
        return new ResponseEntity<>(addedEntities, HttpStatus.CREATED);
    }
}
