package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.flightDelay.flightdelayapi.shared.dataImport.DataImportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/pre-departure", produces="application/json")
public class PreDepartureDelayController {

    @Value("${import.preDepartureDelayConverterName}")
    private String preDepartureDelayConverterName;

    private final PreDepartureDelayServiceImpl preDepartureDelayService;

    private final DataImportService dataImportService;

    @PutMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> updateFromFile() {
        return dataImportService.importFromFile(preDepartureDelayService, preDepartureDelayConverterName);
    }

    @PutMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public List<?> update(@RequestBody List<PreDepartureDelay> preDepartureDelays) {
        return preDepartureDelayService.updateFromJson(preDepartureDelays);
    }
}
