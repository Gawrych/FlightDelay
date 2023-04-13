package com.flightDelay.flightdelayapi.dataImport;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataImportServiceImpl implements DataImportService {

    private static final String PATH_TO_PYTHON_CONVERTER_SCRIPT =
            "scripts/ExcelToJsonConverter.py";

    private final ObjectMapper objectMapper;

    public String importDataFromFiles() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python3", PATH_TO_PYTHON_CONVERTER_SCRIPT);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException | InterruptedException e) {
            //TODO: Add exceptionClass
            return "[{\"status_code\": \"500\",\"error\": \"Script does not exist or was interrupted\"}]";
        }
    }
}
