package com.flightDelay.flightdelayapi.additionalTime;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/additional-time", produces="application/json")
public class AdditionalTimeController {

    @Value("${import.additionalTimeConverterName}")
    private String additionalTimeConverterName;

    private final AdditionalTimeService additionalTimeService;

    private final DataImportService dataImportService;

    @PutMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> updateFromFile() {
        return dataImportService.importFromFile(additionalTimeService, additionalTimeConverterName);
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> update(@RequestBody List<AdditionalTime> additionalTimes) {
        return additionalTimeService.updateFromJson(additionalTimes);
    }
}
