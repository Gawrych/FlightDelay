package com.flightDelay.flightdelayapi.traffic;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/traffic", produces="application/json")
public class TrafficController {

    @Value("${import.trafficConverterName}")
    private String trafficConverterName;

    private final TrafficServiceImpl trafficService;

    private final DataImportService dataImportService;

    @PutMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> updateFromFile() {
        return dataImportService.importFromFile(trafficService, trafficConverterName);
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> update(@RequestBody List<Traffic> trafficRecords) {
        return trafficService.updateFromJson(trafficRecords);
    }
}
