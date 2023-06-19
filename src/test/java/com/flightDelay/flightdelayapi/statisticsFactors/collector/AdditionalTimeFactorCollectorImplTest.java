package com.flightDelay.flightdelayapi.statisticsFactors.collector;

import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeDto;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeService;
import com.flightDelay.flightdelayapi.additionalTime.AdditionalTimeStage;
import com.flightDelay.flightdelayapi.statisticsFactors.calculator.AdditionalTimeFactorsCalculator;
import com.flightDelay.flightdelayapi.statisticsFactors.creator.StatisticReportCreator;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.AdditionalTimeFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.EntityStatisticFactor;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.StatisticFactorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("The additional time factor collector")
class AdditionalTimeFactorCollectorImplTest {

    @Mock
    private AdditionalTimeFactorsCalculator additionalTimeFactorsCalculator;

    @Mock
    private StatisticReportCreator statisticReportCreator;

    @Mock
    private AdditionalTimeService additionalTimeService;

    @Captor
    private ArgumentCaptor<List<AdditionalTimeDto>> dtoCaptor;

    @Captor
    private ArgumentCaptor<EntityStatisticFactor[]> entityArrayCaptor;

    @Captor
    private ArgumentCaptor<EntityStatisticFactor> entityCaptor;

    @Captor
    private ArgumentCaptor<AdditionalTimeFactor> additionalTimeFactorCaptor;

    @Captor
    private ArgumentCaptor<Double> doubleCaptor;

    private AdditionalTimeFactorCollectorImpl additionalTimeFactorCollector;

    @BeforeEach
    void setUp() {
        this.additionalTimeFactorCollector = spy(new AdditionalTimeFactorCollectorImpl(
                additionalTimeFactorsCalculator,
                statisticReportCreator,
                additionalTimeService
        ));
    }

    @Nested
    @DisplayName("correctly collects factors")
    class ReturnFilledList {

        @Nested
        @DisplayName("when match")
        class MatchFactor {

            @Test
            @DisplayName("factor TOP_MONTH_DELAY_IN_TAXI_IN_AND_ASMA with external calculator")
            void CollectFactors_WhenPassTopMonthDelayInTaxiInAndAsmaFactor_ThenMatchWithExternalCalculator() {
                // Given
                AdditionalTimeFactor factorName = AdditionalTimeFactor.TOP_MONTH_DELAY_IN_TAXI_IN_AND_ASMA;

                // When
                additionalTimeFactorCollector.calculateFactor(factorName, List.of());

                // Then
                verify(additionalTimeFactorsCalculator).calculateTopDelayMonth(anyList());
                verifyNoMoreInteractions(additionalTimeFactorsCalculator);
            }

            @Test
            @DisplayName("factor TOP_MONTH_DELAY_IN_TAXI_OUT with external calculator")
            void CollectFactors_WhenPassTopMonthDelayInTaxiOutFactor_ThenMatchWithExternalCalculator() {
                // Given
                AdditionalTimeFactor factorName = AdditionalTimeFactor.TOP_MONTH_DELAY_IN_TAXI_OUT;

                // When
                additionalTimeFactorCollector.calculateFactor(factorName, List.of());

                // Then
                verify(additionalTimeFactorsCalculator).calculateTopDelayMonth(anyList());
                verifyNoMoreInteractions(additionalTimeFactorsCalculator);
            }
        }

        @Nested
        @DisplayName("when pass")
        class PassFactor {

            @Test
            @DisplayName("when pass dto list to superclass method")
            void CollectFactors_WhenPassDtoListFromDatabase_ThenReturnListOfFactors() {
                // Given
                List<AdditionalTimeDto> expectedList = List.of(new AdditionalTimeDto());

                given(additionalTimeService.findAllLatestByAirport(anyString())).willReturn(expectedList);

                doReturn(List.of()).when(additionalTimeFactorCollector)
                        .collectFactors(anyString(), anyList(), any(AdditionalTimeFactor[].class));

                // When
                additionalTimeFactorCollector.collect("AAAA");

                // Then
                verify(additionalTimeFactorCollector).collectFactors(
                        anyString(),
                        dtoCaptor.capture(),
                        any(AdditionalTimeFactor[].class));

                List<AdditionalTimeDto> actualList = dtoCaptor.getValue();

                then(actualList).isEqualTo(expectedList);
            }

            @Test
            @DisplayName("when pass additional time factors array to superclass method")
            void CollectFactors_WhenPassAdditionalTimeFactorArray_ThenReturnListOfFactors() {
                // Given
                List<AdditionalTimeDto> additionalTimeDtos = List.of(new AdditionalTimeDto());

                given(additionalTimeService.findAllLatestByAirport(anyString())).willReturn(additionalTimeDtos);

                doReturn(List.of()).when(additionalTimeFactorCollector)
                        .collectFactors(anyString(), anyList(), any(AdditionalTimeFactor[].class));

                // When
                additionalTimeFactorCollector.collect("AAAA");

                // Then
                EntityStatisticFactor[] expectedEntity = AdditionalTimeFactor.values();

                verify(additionalTimeFactorCollector).collectFactors(anyString(), anyList(), entityArrayCaptor.capture());

                EntityStatisticFactor[] actualEntity = entityArrayCaptor.getValue();

                then(actualEntity).isEqualTo(expectedEntity);
            }

            @Test
            @DisplayName("factor AVERAGE_DELAY_IN_TAXI_IN_AND_ASMA and calculated value to report creator")
            void CollectFactors_WhenGetsAverageDelayInTaxiInAndAsmaAndValue_ThenPassToPrecisionFactorCreator() {
                // Given
                AdditionalTimeFactor expectedFactor = AdditionalTimeFactor.AVERAGE_DELAY_IN_TAXI_IN_AND_ASMA;
                double expectedValue = 10.0d;
                given(additionalTimeFactorsCalculator.calculateAverageFromList(anyList())).willReturn(expectedValue);

                // When
                additionalTimeFactorCollector.calculateFactor(expectedFactor, List.of());

                // Then
                verify(statisticReportCreator).create(additionalTimeFactorCaptor.capture(), doubleCaptor.capture());

                AdditionalTimeFactor actualFactor = additionalTimeFactorCaptor.getValue();
                double actualValue = doubleCaptor.getValue();

                then(actualFactor).isEqualTo(expectedFactor);
                then(actualValue).isEqualTo(expectedValue);
            }

            @Test
            @DisplayName("factor AVERAGE_DELAY_IN_TAXI_OUT and calculated value to report creator")
            void CollectFactors_WhenGetsAverageDelayInTaxiOutAndValue_ThenPassToPrecisionFactorCreator() {
                // Given
                AdditionalTimeFactor expectedFactor = AdditionalTimeFactor.AVERAGE_DELAY_IN_TAXI_OUT;
                double expectedValue = 10.0d;
                given(additionalTimeFactorsCalculator.calculateAverageFromList(anyList())).willReturn(expectedValue);

                // When
                additionalTimeFactorCollector.calculateFactor(expectedFactor, List.of());

                // Then
                verify(statisticReportCreator).create(additionalTimeFactorCaptor.capture(), doubleCaptor.capture());

                AdditionalTimeFactor actualFactor = additionalTimeFactorCaptor.getValue();
                double actualValue = doubleCaptor.getValue();

                then(actualFactor).isEqualTo(expectedFactor);
                then(actualValue).isEqualTo(expectedValue);
            }
        }

        @ParameterizedTest(name = "{index} : {0} factor name")
        @EnumSource(AdditionalTimeFactor.class)
        @DisplayName("when no data factor method pass to report creator")
        void CollectFactors_WhenNoDataFactorGetsDataFromSuperclass_ThenPassToPrecisionFactorCreator(
                AdditionalTimeFactor additionalTimeFactor) {

            // When
            additionalTimeFactorCollector.getNoDataFactor(additionalTimeFactor);

            // Then
            verify(statisticReportCreator).create(additionalTimeFactorCaptor.capture());

            AdditionalTimeFactor actualTrafficFactor = additionalTimeFactorCaptor.getValue();

            then(actualTrafficFactor).isEqualTo(additionalTimeFactor);
        }

        @Test
        @DisplayName("when correctly filter dtos list by TAXI_OUT stage")
        void CollectFactors_WhenCorrectlyFilterDtosListByTaxiOutStage_ThenReturnFilteredList() {
            // Given
            AdditionalTimeFactor expectedFactor = AdditionalTimeFactor.TOP_MONTH_DELAY_IN_TAXI_OUT;

            AdditionalTimeDto taxiOutStage = mock(AdditionalTimeDto.class);
            AdditionalTimeDto taxiInAndAsmaStage = mock(AdditionalTimeDto.class);

            given(taxiOutStage.getStage()).willReturn(AdditionalTimeStage.TAXI_OUT);
            given(taxiInAndAsmaStage.getStage()).willReturn(AdditionalTimeStage.ASMA);

            List<AdditionalTimeDto> dtos = List.of(taxiOutStage, taxiInAndAsmaStage);

            // When
            additionalTimeFactorCollector.calculateFactor(expectedFactor, dtos);

            // Then
            List<AdditionalTimeDto> expectedList = List.of(taxiOutStage);

            verify(additionalTimeFactorsCalculator).calculateTopDelayMonth(dtoCaptor.capture());

            List<AdditionalTimeDto> actualList = dtoCaptor.getValue();

            then(actualList).isEqualTo(expectedList);
        }

        @Test
        @DisplayName("when correctly filter dtos list by TAXI_IN and ASMA stage")
        void CollectFactors_WhenCorrectlyFilterDtosListByTaxiInAndAsmaStage_ThenReturnFilteredList() {
            // Given
            AdditionalTimeFactor expectedFactor = AdditionalTimeFactor.TOP_MONTH_DELAY_IN_TAXI_IN_AND_ASMA;

            AdditionalTimeDto taxiOutStage = mock(AdditionalTimeDto.class);
            AdditionalTimeDto taxiInAndAsmaStage = mock(AdditionalTimeDto.class);

            given(taxiOutStage.getStage()).willReturn(AdditionalTimeStage.TAXI_OUT);
            given(taxiInAndAsmaStage.getStage()).willReturn(AdditionalTimeStage.ASMA);

            List<AdditionalTimeDto> dtos = List.of(taxiOutStage, taxiInAndAsmaStage);

            // When
            additionalTimeFactorCollector.calculateFactor(expectedFactor, dtos);

            // Then
            List<AdditionalTimeDto> expectedList = List.of(taxiInAndAsmaStage);

            verify(additionalTimeFactorsCalculator).calculateTopDelayMonth(dtoCaptor.capture());

            List<AdditionalTimeDto> actualList = dtoCaptor.getValue();

            then(actualList).isEqualTo(expectedList);
        }

        @Test
        @DisplayName("when factor is not valid create no data report")
        void CollectFactors_WhenFactorIsNotValid_ThenCreateNoDataReport() {
            // Given
            EntityStatisticFactor notValidFactor = mock(EntityStatisticFactor.class);
            given(notValidFactor.getType()).willReturn(StatisticFactorType.VALUE_WITH_TEXT);

            // When
            additionalTimeFactorCollector.calculateFactor(notValidFactor, List.of());

            // Then
            verify(statisticReportCreator).create(entityCaptor.capture());
            verifyNoInteractions(additionalTimeFactorsCalculator);

            EntityStatisticFactor actualFactor = entityCaptor.getValue();

            then(actualFactor).isEqualTo(notValidFactor);
        }
    }
}