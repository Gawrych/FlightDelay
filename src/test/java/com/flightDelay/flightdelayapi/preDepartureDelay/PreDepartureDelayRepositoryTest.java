package com.flightDelay.flightdelayapi.preDepartureDelay;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@DisplayName("The pre departure delay repository")
class PreDepartureDelayRepositoryTest {


    @Autowired
    private PreDepartureDelayRepository preDepartureDelayRepository;

    @Test
    @DisplayName("returns all records from database that are newer than input date")
    void FindAllByAirportWithDateAfter_WhenPassDateAndAirport_ThenReturnFromDatabaseRecordsAfterPassedDate() {
        // Given
        PreDepartureDelay firstAdditionalTimeRecord = PreDepartureDelay.builder()
                .year(1)
                .monthNum(1)
                .date(LocalDate.ofEpochDay(1))
                .airportCode("AAAA")
                .build();

        PreDepartureDelay secondAdditionalTimeRecord = PreDepartureDelay.builder()
                .year(1)
                .monthNum(1)
                .date(LocalDate.ofEpochDay(3))
                .airportCode("AAAA")
                .build();

        preDepartureDelayRepository.save(firstAdditionalTimeRecord);
        preDepartureDelayRepository.save(secondAdditionalTimeRecord);

        // When
        List<PreDepartureDelay> actualRecords =
                preDepartureDelayRepository.findAllByAirportWithDateAfter("AAAA", LocalDate.ofEpochDay(2));

        // Then
        then(actualRecords).hasSize(1);
        then(actualRecords)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("generatedId")
                .containsExactly(secondAdditionalTimeRecord);
    }
}