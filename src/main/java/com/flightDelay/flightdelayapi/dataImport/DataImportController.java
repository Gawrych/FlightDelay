package com.flightDelay.flightdelayapi.dataImport;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/data")
public class DataImportController {

    private final DataImportServiceImpl dataImportService;

    private final ObjectMapper objectMapper;

    @PutMapping("/files-import")
    public ResponseEntity<String> importData() {
        String responseJson = dataImportService.importDataFromFiles();
        try {
            JsonNode[] jsonNodes = objectMapper.readValue(responseJson, JsonNode[].class);
            for(JsonNode jsonNode : jsonNodes) {
                HttpStatusCode httpStatusCode = HttpStatusCode.valueOf(jsonNode.get("status_code").asInt());
                if (!httpStatusCode.is2xxSuccessful()) {
                    return new ResponseEntity<>(responseJson, httpStatusCode);
                }
            }
            return new ResponseEntity<>(responseJson, HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("Error processing JSON data " + e + "\n" + responseJson,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
