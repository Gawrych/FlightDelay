package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.shared.exception.LackOfCrucialDataException;
import com.flightDelay.flightdelayapi.shared.exception.resource.ResourceNotFoundException;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("The statistic factor collector")
class StatisticFactorCollectorTest {

    @Spy
    private StatisticFactorCollector<DelayEntityDto> statisticFactorCollector;

    @Captor
    private ArgumentCaptor<EntityStatisticFactor> entityCaptor;

    @Captor
    private ArgumentCaptor<List<DelayEntityDto>> dtosCaptor;


    @Nested
    @DisplayName("returns correctly filled list")
    class ReturnFilledList {

        @Test
        @DisplayName("when it handles every element in the entity factor array")
        void CollectFactors_WhenHandlesEveryElement_ThenReturnFilledList() {
            // Given
            EntityStatisticFactor[] entityFactorArray = {
                    mock(EntityStatisticFactor.class),
                    mock(EntityStatisticFactor.class),
                    mock(EntityStatisticFactor.class)};

            // When
            statisticFactorCollector.collectFactors("AAAA", List.of(), entityFactorArray);

            // Then
            verify(statisticFactorCollector, times(3)).calculateFactor(entityCaptor.capture(), anyList());

            List<EntityStatisticFactor> actualEntities = entityCaptor.getAllValues();

            then(actualEntities).isEqualTo(Arrays.asList(entityFactorArray));
        }

        @Nested
        @DisplayName("when create complete factors by")
        class CompleteFactors {

            @Test
            @DisplayName("iterate through the entity statistic factor array")
            void CollectFactors_WhenIterateThroughFactorArray_ThenCreateCompleteFactors() {
                // Given
                PrecisionReport completeFactor = mock(PrecisionReport.class);
                given(statisticFactorCollector.calculateFactor(any(), anyList())).willReturn(completeFactor);

                EntityStatisticFactor[] entityFactor = {
                        mock(EntityStatisticFactor.class),
                        mock(EntityStatisticFactor.class)};

                // When
                List<PrecisionReport> actualFactor =
                        statisticFactorCollector.collectFactors("AAAA", List.of(), entityFactor);

                // Then
                SoftAssertions.assertSoftly(softly -> {
                    softly.assertThat(actualFactor).hasSize(2);
                    softly.assertThat(actualFactor.get(0)).isEqualTo(completeFactor);
                    softly.assertThat(actualFactor.get(1)).isEqualTo(completeFactor);
                });
            }

            @Test
            @DisplayName("a correctly pass input parameters to factor calculator")
            void CollectFactors_WhenPassInputParameters_ThenCreateCompleteFactors() {
                // Given
                List<DelayEntityDto> dtosList = List.of(mock(DelayEntityDto.class));
                EntityStatisticFactor entityFactor = mock(EntityStatisticFactor.class);

                EntityStatisticFactor[] entityFactorArray = {entityFactor};

                // When
                statisticFactorCollector.collectFactors("AAAA", dtosList, entityFactorArray);

                // Then
                verify(statisticFactorCollector).calculateFactor(entityCaptor.capture(), dtosCaptor.capture());

                EntityStatisticFactor actualEntity = entityCaptor.getValue();
                List<DelayEntityDto> actualDtos = dtosCaptor.getValue();

                then(actualDtos).isEqualTo(dtosList);
                then(actualEntity).isEqualTo(entityFactor);
            }
        }

        @Nested
        @DisplayName("when create no data factors")
        class NoDataFactors {

            @Test
            @DisplayName("when throw a lack of crucial data exception in factor calculator")
            void CollectFactors_WhenThrowLackOfCrucialDataException_ThenReturnNoDataFactor() {
                // Given
                given(statisticFactorCollector.calculateFactor(any(), anyList()))
                        .willThrow(LackOfCrucialDataException.class);

                EntityStatisticFactor[] entityFactors = {mock(EntityStatisticFactor.class)};

                // When
                statisticFactorCollector.collectFactors("AAAA", List.of(), entityFactors);

                // Then
                verify(statisticFactorCollector).getNoDataFactor(any());
            }

            @Test
            @DisplayName("when throw a resource not found exception in factor calculator")
            void CollectFactors_WhenThrowResourceNotFoundException_ThenReturnNoDataFactor() {
                // Given
                given(statisticFactorCollector.calculateFactor(any(), anyList()))
                        .willThrow(ResourceNotFoundException.class);

                EntityStatisticFactor[] entityFactors = {mock(EntityStatisticFactor.class)};

                // When
                statisticFactorCollector.collectFactors("AAAA", List.of(), entityFactors);

                // Then
                verify(statisticFactorCollector).getNoDataFactor(any());
            }
        }
    }

    @Nested
    @DisplayName("return empty list")
    class ReturnsEmptyList {

        @Test
        @DisplayName("when entity factor array is empty")
        void CollectFactors_WhenEntityFactorArrayIsEmpty_ThenReturnEmptyList() {
            // Given
            EntityStatisticFactor[] entityFactors = {};

            // When
            List<PrecisionReport> actualList =
                    statisticFactorCollector.collectFactors("AAAA", List.of(), entityFactors);

            // Then
            then(actualList).isEmpty();
        }
    }
}