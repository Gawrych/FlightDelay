package com.flightDelay.flightdelayapi.dataImport;

import com.flightDelay.flightdelayapi.shared.UpdateFromJson;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataImportServiceImpl implements DataImportService {

    private static final String CONVERTER_BASE_PATH = "scripts/converter/";
    public static final String ARRIVAL_DELAY_SCRIPT_NAME = "ArrivalDelayScript.py";
    public static final String DEPARTURE_ADDITIONAL_TIME_SCRIPT_NAME = "DepartureAdditionalTimeScript.py";
    public static final String PRE_DEPARTURE_DELAY_SCRIPT_NAME = "PreDepartureDelayScript.py";
    public static final String TRAFFIC_SCRIPT_NAME = "TrafficScript.py";

    public ResponseEntity<String> importFromFile(UpdateFromJson entityAbleToBeUpdatedByJson, String scriptName) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", CONVERTER_BASE_PATH+scriptName);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String newDataInJson = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                return new ResponseEntity<>("Error during converting JSON data. Exit code: "
                        + exitCode + " " + newDataInJson, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return entityAbleToBeUpdatedByJson.updateFromJson(newDataInJson);
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
