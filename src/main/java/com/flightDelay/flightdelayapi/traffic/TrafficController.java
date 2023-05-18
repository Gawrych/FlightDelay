package com.flightDelay.flightdelayapi.traffic;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/traffic")
public class TrafficController {

    @Value("${import.trafficConverterName}")
    private String trafficConverterName;

    private final TrafficServiceImpl trafficService;

    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file")
    public ResponseEntity<List<?>> updateFromFile() {
        List<?> addedEntities = dataImportService.importFromFile(trafficService, trafficConverterName);
        return new ResponseEntity<>(addedEntities, HttpStatus.CREATED);
    }

    @PutMapping("/json")
    public ResponseEntity<List<?>> update(@RequestBody String dataInJson) {
        List<?> addedEntities = trafficService.updateFromJson(dataInJson);
        return new ResponseEntity<>(addedEntities, HttpStatus.CREATED);
    }
}
