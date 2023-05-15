package com.flightDelay.flightdelayapi.traffic;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/traffic")
public class TrafficController {

    @Value("${import.trafficConverterName}")
    private String trafficConverterName;

    private final TrafficServiceImpl trafficService;
    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file-update")
    public ResponseEntity<String> updateFromFile() {
        String newData = dataImportService.importFromFile(trafficService, trafficConverterName);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody String dataInJson) {
        String newData = trafficService.updateFromJson(dataInJson);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);

    }
}
