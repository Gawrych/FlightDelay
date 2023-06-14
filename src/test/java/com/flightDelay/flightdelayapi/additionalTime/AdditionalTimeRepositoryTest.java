package com.flightDelay.flightdelayapi.additionalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@DisplayName("AdditionalTimeRepository Tests")
class AdditionalTimeRepositoryTest {

    @Autowired
    private AdditionalTimeRepository additionalTimeRepository;

    @Test
    @DisplayName("FindAllByAirportWithDateAfter - Find only with date after")
    void FindAllByAirportWithDateAfter_WhenPassDateAndAirport_ThenReturnFromDatabaseRecordsAfterPassedDate() {
        // Given
        AdditionalTime firstAdditionalTimeRecord = AdditionalTime.builder()
                .year(1)
                .monthNum(1)
                .flightDate(LocalDate.ofEpochDay(1))
                .stage("A")
                .airportCode("AAAA")
                .totalFlight(1)
                .totalReferenceTimeInMinutes(1)
                .totalAdditionalTimeInMinutes(1)
                .build();

        AdditionalTime secondAdditionalTimeRecord = AdditionalTime.builder()
                .year(1)
                .monthNum(1)
                .flightDate(LocalDate.ofEpochDay(3))
                .stage("A")
                .airportCode("AAAA")
                .totalFlight(1)
                .totalReferenceTimeInMinutes(1)
                .totalAdditionalTimeInMinutes(1)
                .build();

        additionalTimeRepository.save(firstAdditionalTimeRecord);
        additionalTimeRepository.save(secondAdditionalTimeRecord);

        // When
        List<AdditionalTime> actualRecords =
                additionalTimeRepository.findAllByAirportWithDateAfter("AAAA", LocalDate.ofEpochDay(2));

        // Then
        then(actualRecords).hasSize(1);
        then(actualRecords)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("generatedId")
                .containsExactly(secondAdditionalTimeRecord);
    }
}