package com.flightDelay.flightdelayapi.shared.dataImport;

import com.flightDelay.flightdelayapi.shared.exception.importData.JsonFileDataConverterFailed;
import com.flightDelay.flightdelayapi.shared.exception.importData.JsonFileDataConverterUnexpectedExitCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataImportServiceImpl implements DataImportService {

    @Value("${import.convertersDirectoryPath}")
    private String convertersDirectoryPath;

    @Value("${import.commandToCallConverter}")
    private String commandToCallConverter;

    @Override
    public List<?> importFromFile(UpdateFromJson service, String scriptName) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    commandToCallConverter,
                    convertersDirectoryPath + scriptName);

            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String newDataInJson = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new JsonFileDataConverterUnexpectedExitCodeException(scriptName, exitCode);
            }

            return service.updateFromJson(newDataInJson);

        } catch (IOException | InterruptedException e) {
            throw new JsonFileDataConverterFailed(scriptName);
        }
    }
}
