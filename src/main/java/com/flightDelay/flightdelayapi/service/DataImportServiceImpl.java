package com.flightDelay.flightdelayapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataImportServiceImpl implements DataImportService {

    private static final String PATH_TO_PYTHON_CONVERTER_SCRIPT =
            "/home/broslaw/Programming/flight-delay-api/src/main/java/com/flightDelay/flightdelayapi/converter/ExcelToJsonConverter.py";

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
            throw new RuntimeException(e);
        }
    }

    private String readProcessOutput(InputStream inputStream) {
        try {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
