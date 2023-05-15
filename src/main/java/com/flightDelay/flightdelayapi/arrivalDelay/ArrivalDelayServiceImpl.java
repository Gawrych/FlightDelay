package com.flightDelay.flightdelayapi.arrivalDelay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.exception.importData.ProcessingJsonDataFailedException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArrivalDelayServiceImpl implements ArrivalDelayService {

    private final ArrivalDelayRepository arrivalDelayRepository;

    private final ResourceBundleMessageSource messageSource;

    private final AirportService airportService;

    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public String updateFromJson(String newDataInJsonString) {
        try {
            TypeReference<List<ArrivalDelay>> typeReference = new TypeReference<>() {};
            List<ArrivalDelay> arrivalDelayReports = objectMapper.readValue(newDataInJsonString, typeReference);
            arrivalDelayReports.forEach(this::save);

        } catch (JsonProcessingException e) {
            throw new ProcessingJsonDataFailedException(ArrivalDelayServiceImpl.class.getName());
        }

        return newDataInJsonString;
    }

    @Override
    public void save(ArrivalDelay arrivalDelay) {
        if (!arrivalDelayRepository.existsByGeneratedId(arrivalDelay.generateId())) {
            arrivalDelay.setAirportBidirectionalRelationshipByCode(arrivalDelay.getAirportCode(), airportService);
            arrivalDelayRepository.save(arrivalDelay);
        }
    }
}
