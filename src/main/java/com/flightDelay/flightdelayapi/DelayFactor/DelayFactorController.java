package com.flightDelay.flightdelayapi.DelayFactor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.flight.Flight;
import com.flightDelay.flightdelayapi.shared.DateProcessor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/factors")
public class DelayFactorController {

    private final ObjectMapper objectMapper;
    private final DelayFactorService delayFactorService;

    @PostMapping("/hour")
    public ResponseEntity<?> getFactorsToSpecifyTime(
            @RequestParam("airport") String airportIdent,
            @RequestParam("time") @DateTimeFormat(pattern = DateProcessor.WEATHER_API_DATE_WITH_T_PATTERN) Date time,
            @RequestHeader("Accept-Language") String acceptLanguage) {

        LocaleContextHolder.setLocale(Locale.forLanguageTag(acceptLanguage));

        List<DelayFactor> delayFactors = delayFactorService.getFactorsByHour(airportIdent, time);

        return new ResponseEntity<>(delayFactors, HttpStatus.OK);
    }

//    @PostMapping("/day")
//    public ResponseEntity<?> getFactorsToNextDay(@RequestBody String flightData,
//                                                       @RequestHeader(value="Accept-Language") String acceptLanguage) {
//        LocaleContextHolder.setLocale(Locale.forLanguageTag(acceptLanguage));
//        try {
//            Flight flightToCheck = objectMapper.readValue(flightData, Flight.class);
//            List<DelayFactor> factors = delayFactorService.calculateFactors(flightToCheck);
//            return new ResponseEntity<>("factors", HttpStatus.OK);
//        } catch (JsonProcessingException e) {
//            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/weeks")
//    public ResponseEntity<?> getFactorsToNext16Days(@RequestBody String flightData,
//                                                       @RequestHeader(value="Accept-Language") String acceptLanguage) {
//        LocaleContextHolder.setLocale(Locale.forLanguageTag(acceptLanguage));
//        try {
//            Flight flightToCheck = objectMapper.readValue(flightData, Flight.class);
//            List<DelayFactor> factors = delayFactorService.calculateFactors(flightToCheck);
//            return new ResponseEntity<>("factors", HttpStatus.OK);
//        } catch (JsonProcessingException e) {
//            return new ResponseEntity<>("Error processing JSON data " + e, HttpStatus.BAD_REQUEST);
//        }
//    }
}
