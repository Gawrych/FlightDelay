package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pre-departure")
public class PreDepartureDelayController {

    @Value("${import.preDepartureDelayConverterName}")
    private String preDepartureDelayConverterName;

    private final PreDepartureDelayServiceImpl preDepartureDelayService;

    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file-update")
    public ResponseEntity<String> updateFromFile() {
        String newData = dataImportService.importFromFile(preDepartureDelayService, preDepartureDelayConverterName);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody String dataInJson) {
        String newData = preDepartureDelayService.updateFromJson(dataInJson);
        return new ResponseEntity<>(newData, HttpStatus.CREATED);
    }
}
