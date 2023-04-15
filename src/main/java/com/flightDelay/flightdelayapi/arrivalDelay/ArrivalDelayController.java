package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flightDelay.flightdelayapi.dataImport.DataImportServiceImpl.ARRIVAL_DELAY_SCRIPT_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/delays")
public class ArrivalDelayController {

    private final ArrivalDelayServiceImpl arrivalDelayService;
    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file-update")
    public ResponseEntity<String> updateFromFile() {
        return dataImportService.importFromFile(arrivalDelayService, ARRIVAL_DELAY_SCRIPT_NAME);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody String dataInJson) {
        return arrivalDelayService.updateFromJson(dataInJson);
    }
}
