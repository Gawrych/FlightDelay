package com.flightDelay.flightdelayapi.shared.dataImport;

import com.flightDelay.flightdelayapi.shared.exception.importData.JsonFileDataConverterFailed;
import com.flightDelay.flightdelayapi.shared.exception.importData.JsonFileDataConverterUnexpectedExitCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataImportServiceImpl implements DataImportService {

    @Value("${import.convertersDirectoryPath}")
    private String convertersDirectoryPath;

    @Value("${import.commandToCallConverter}")
    private String commandToCallConverter;

    @Override
    public String importFromFile(UpdateFromJson entityAbleToBeUpdatedByJson, String scriptName) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandToCallConverter, convertersDirectoryPath+scriptName);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String newDataInJson = reader.lines().collect(Collectors.joining(System.lineSeparator()));

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new JsonFileDataConverterUnexpectedExitCodeException(scriptName, exitCode);
            }

            return entityAbleToBeUpdatedByJson.updateFromJson(newDataInJson);

        } catch (IOException | InterruptedException e) {
            // TODO: Log error with e here
            throw new JsonFileDataConverterFailed(scriptName);
        }
    }
}
