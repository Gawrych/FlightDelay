package com.flightDelay.flightdelayapi.DelayFactor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.shared.Flight;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/factors")
public class DelayFactorController {

    private final ObjectMapper objectMapper;
    private final DelayFactorService delayFactorService;

    @PostMapping("/hour")
    public List<DelayFactor> getFactorsToSpecifyTime(@RequestBody @Validated Flight flight,
                                                     @RequestHeader("Accept-Language") String acceptLanguage) {
        LocaleContextHolder.setLocale(Locale.forLanguageTag(acceptLanguage));
        return delayFactorService.getFactorsByHour(flight);
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
