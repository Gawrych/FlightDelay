package com.flightDelay.flightdelayapi.traffic;

import com.flightDelay.flightdelayapi.dataImport.DataImportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.flightDelay.flightdelayapi.dataImport.DataImportServiceImpl.TRAFFIC_SCRIPT_NAME;

@RestController
@RequiredArgsConstructor
@RequestMapping("/traffic")
public class TrafficController {

    private final TrafficServiceImpl trafficService;
    private final DataImportServiceImpl dataImportService;

    @PutMapping("/file-update")
    public ResponseEntity<String> updateFromFile() {
        return dataImportService.importFromFile(trafficService, TRAFFIC_SCRIPT_NAME);
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@RequestBody String dataInJson) {
        return trafficService.updateFromJson(dataInJson);
    }
}
