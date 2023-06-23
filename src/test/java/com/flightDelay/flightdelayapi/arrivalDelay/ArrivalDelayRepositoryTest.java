package com.flightDelay.flightdelayapi.arrivalDelay;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@DisplayName("The arrival delay repository")
class ArrivalDelayRepositoryTest {

    @Autowired
    private ArrivalDelayRepository arrivalDelayRepository;

    @Test
    @DisplayName("returns all records from database that are newer than input date")
    void FindAllByAirportWithDateAfter_WhenPassDateAndAirport_ThenReturnFromDatabaseRecordsAfterPassedDate() {
        // Given
        ArrivalDelay firstAdditionalTimeRecord = ArrivalDelay.builder()
                .year(1)
                .month(1)
                .date(LocalDate.ofEpochDay(1))
                .airportCode("AAAA")
                .build();

        ArrivalDelay secondAdditionalTimeRecord = ArrivalDelay.builder()
                .year(1)
                .month(1)
                .date(LocalDate.ofEpochDay(3))
                .airportCode("AAAA")
                .build();

        arrivalDelayRepository.save(firstAdditionalTimeRecord);
        arrivalDelayRepository.save(secondAdditionalTimeRecord);

        // When
        List<ArrivalDelay> actualRecords =
                arrivalDelayRepository.findAllByAirportWithDateAfter("AAAA", LocalDate.ofEpochDay(2));

        // Then
        then(actualRecords).hasSize(1);
        then(actualRecords)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("generatedId")
                .containsExactly(secondAdditionalTimeRecord);
    }
}