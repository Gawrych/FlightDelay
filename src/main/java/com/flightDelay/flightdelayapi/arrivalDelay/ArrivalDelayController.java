package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.airport.AirportRepository;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.departureAdditionalTime.DepartureAdditionalTime;
import com.flightDelay.flightdelayapi.shared.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/update/delays")
public class ArrivalDelayController {

    @Value("${import.arrivalDelayConverterName}")
    private String arrivalDelayConverterName;

    private final ArrivalDelayServiceImpl arrivalDelayService;

    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file")
    public ResponseEntity<String> updateFromFile() {
        String newData = dataImportService.importFromFile(arrivalDelayService, arrivalDelayConverterName);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);
    }

    @PutMapping("/json")
    public ResponseEntity<String> update(@RequestBody String dataInJson) {
        String newData = arrivalDelayService.updateFromJson(dataInJson);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);
    }
}
