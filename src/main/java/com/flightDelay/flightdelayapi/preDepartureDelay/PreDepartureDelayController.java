package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.flightDelay.flightdelayapi.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flightDelay.flightdelayapi.dataImport.DataImportServiceImpl.PRE_DEPARTURE_DELAY_SCRIPT_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pre-departure")
public class PreDepartureDelayController {

    private final PreDepartureDelayServiceImpl preDepartureDelayService;

    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file-update")
    public ResponseEntity<String> updateFromFile() {
        return dataImportService.importFromFile(preDepartureDelayService, PRE_DEPARTURE_DELAY_SCRIPT_NAME);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody String dataInJson) {
        return preDepartureDelayService.updateFromJson(dataInJson);
    }
}
