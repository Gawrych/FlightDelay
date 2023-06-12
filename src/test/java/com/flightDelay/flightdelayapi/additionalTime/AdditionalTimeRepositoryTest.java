package com.flightDelay.flightdelayapi.additionalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class AdditionalTimeRepositoryTest {

    @Autowired
    private AdditionalTimeRepository additionalTimeRepository;

    @Test
    void whenISaveAdditionalTime_thenAdditionalTimeIsAddedToTheDatabase() {
        // Given
        AdditionalTime newAdditionalTimeRecord = AdditionalTime.builder()
                .year(1)
                .monthNum(1)
                .flightDate(LocalDate.ofEpochDay(1))
                .stage("A")
                .airportCode("AAAA")
                .totalFlight(1)
                .totalReferenceTimeInMinutes(1)
                .totalAdditionalTimeInMinutes(1)
                .build();

        // When
        additionalTimeRepository.save(newAdditionalTimeRecord);

        // Then
        boolean existsInDatabase = additionalTimeRepository.existsByGeneratedId(newAdditionalTimeRecord.generateId());

        assertThat(existsInDatabase).isTrue();
    }
}