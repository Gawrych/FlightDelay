package com.flightDelay.flightdelayapi.traffic;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@DisplayName("The traffic repository")
class TrafficRepositoryTest {

    @Autowired
    private TrafficRepository trafficRepository;

    @Test
    @DisplayName("returns all records from database that are newer than input date")
    void FindAllByAirportWithDateAfter_WhenPassDateAndAirport_ThenReturnFromDatabaseRecordsAfterPassedDate() {
        // Given
        Traffic firstAdditionalTimeRecord = Traffic.builder()
                .year(1)
                .monthNum(1)
                .flightDate(LocalDate.ofEpochDay(1))
                .airportCode("AAAA")
                .build();

        Traffic secondAdditionalTimeRecord = Traffic.builder()
                .year(1)
                .monthNum(1)
                .flightDate(LocalDate.ofEpochDay(3))
                .airportCode("AAAA")
                .build();

        trafficRepository.save(firstAdditionalTimeRecord);
        trafficRepository.save(secondAdditionalTimeRecord);

        // When
        List<Traffic> actualRecords =
                trafficRepository.findAllByAirportWithDateAfter("AAAA", LocalDate.ofEpochDay(2));

        // Then
        then(actualRecords).hasSize(1);
        then(actualRecords)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("generatedId")
                .containsExactly(secondAdditionalTimeRecord);
    }
}