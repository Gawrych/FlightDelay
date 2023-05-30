package com.flightDelay.flightdelayapi.additionalTime;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/additional-time")
public class AdditionalTimeController {

    @Value("${import.additionalTimeConverterName}")
    private String additionalTimeConverterName;

    private final AdditionalTimeService additionalTimeService;


    private final DataImportService dataImportService;

    @PutMapping("/file")
    public ResponseEntity<List<?>> updateFromFile() {
        List<?> addedEntities = dataImportService.importFromFile(additionalTimeService, additionalTimeConverterName);
        return new ResponseEntity<>(addedEntities, HttpStatus.CREATED);
    }

    @PutMapping("/json")
    public ResponseEntity<List<?>> update(@RequestBody String dataInJson) {
        List<?> addedEntities = additionalTimeService.updateFromJson(dataInJson);
        return new ResponseEntity<>(addedEntities, HttpStatus.CREATED);
    }
}
