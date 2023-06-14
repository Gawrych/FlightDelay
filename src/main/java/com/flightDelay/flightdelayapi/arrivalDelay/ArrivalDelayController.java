package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/delays", produces="application/json")
public class ArrivalDelayController {

    @Value("${import.arrivalDelayConverterName}")
    private String arrivalDelayConverterName;

    private final ArrivalDelayService arrivalDelayService;

    private final DataImportService dataImportService;

    @PutMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> updateFromFile() {
        return dataImportService.importFromFile(arrivalDelayService, arrivalDelayConverterName);
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> update(@RequestBody List<ArrivalDelay> arrivalDelays) {
        return arrivalDelayService.updateFromJson(arrivalDelays);
    }
}
