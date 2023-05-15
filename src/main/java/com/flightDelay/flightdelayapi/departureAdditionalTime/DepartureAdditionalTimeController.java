package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/departure")
public class DepartureAdditionalTimeController {

    @Value("${import.departureAdditionalTimeConverterName}")
    private String departureAdditionalTimeConverterName;

    private final DepartureAdditionalTimeServiceImpl departureAdditionalTimeService;

    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file-update")
    public ResponseEntity<String> updateFromFile() {
        String newData = dataImportService.importFromFile(departureAdditionalTimeService, departureAdditionalTimeConverterName);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody String dataInJson) {
        String newData = departureAdditionalTimeService.updateFromJson(dataInJson);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);
    }
}
