package com.flightDelay.flightdelayapi.departureAdditionalTime;

import com.flightDelay.flightdelayapi.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flightDelay.flightdelayapi.dataImport.DataImportServiceImpl.DEPARTURE_ADDITIONAL_TIME_SCRIPT_NAME;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/departure")
public class DepartureAdditionalTimeController {

    private final DepartureAdditionalTimeFromJsonImpl departureAdditionalTimeService;

    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file-update")
    public ResponseEntity<String> updateFromFile() {
        return dataImportService.importFromFile(departureAdditionalTimeService, DEPARTURE_ADDITIONAL_TIME_SCRIPT_NAME);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody String dataInJson) {
        return departureAdditionalTimeService.updateFromJson(dataInJson);
    }
}
