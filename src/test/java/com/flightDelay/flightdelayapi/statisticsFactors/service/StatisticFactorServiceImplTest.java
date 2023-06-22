package com.flightDelay.flightdelayapi.statisticsFactors.service;

import com.flightDelay.flightdelayapi.statisticsFactors.collector.AdditionalTimeFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.collector.ArrivalDelayFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.collector.PreDepartureFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.collector.TrafficFactorCollector;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("The statistic factor service")
class StatisticFactorServiceImplTest {

    @Mock
    private  AdditionalTimeFactorCollector additionalTimeFactorCollector;

    @Mock
    private  PreDepartureFactorCollector preDepartureFactorCollector;

    @Mock
    private  ArrivalDelayFactorCollector arrivalDelayFactorCollector;

    @Mock
    private  TrafficFactorCollector trafficFactorCollector;

    @InjectMocks
    private StatisticFactorServiceImpl statisticFactorService;

    @Test
    @DisplayName("returns map based on list with reports and their names")
    void GetFactorsByPhase_WhenCollectReports_ThenReturnReportsInMap() {
        // Given
        PrecisionReport preDepartureReport = mock(PrecisionReport.class, RETURNS_DEEP_STUBS);
        PrecisionReport additionalTimeReport = mock(PrecisionReport.class, RETURNS_DEEP_STUBS);
        PrecisionReport arrivalDelayReport = mock(PrecisionReport.class, RETURNS_DEEP_STUBS);
        PrecisionReport trafficReport = mock(PrecisionReport.class, RETURNS_DEEP_STUBS);

        given(preDepartureReport.getId().name()).willReturn("preDeparture");
        given(additionalTimeReport.getId().name()).willReturn("additionalTime");
        given(arrivalDelayReport.getId().name()).willReturn("arrivalDelay");
        given(trafficReport.getId().name()).willReturn("traffic");

        given(preDepartureFactorCollector.collect(anyString())).willReturn(List.of(preDepartureReport));
        given(additionalTimeFactorCollector.collect(anyString())).willReturn(List.of(additionalTimeReport));
        given(arrivalDelayFactorCollector.collect(anyString())).willReturn(List.of(arrivalDelayReport));
        given(trafficFactorCollector.collect(anyString())).willReturn(List.of(trafficReport));

        //When
        Map<String, PrecisionReport> actualResult = statisticFactorService.getFactorsByPhase("AAAA");

        // Then
        Map<String, PrecisionReport> expectedMap = Map.of(
                preDepartureReport.getId().name(), preDepartureReport,
                additionalTimeReport.getId().name(), additionalTimeReport,
                arrivalDelayReport.getId().name(), arrivalDelayReport,
                trafficReport.getId().name(), trafficReport);

        then(actualResult).isEqualTo(expectedMap);
    }

    @Nested
    @DisplayName("collects statistic reports")
    class CollectsStatisticsReports {

        @Test
        @DisplayName("from pre departure factor collector")
        void GetFactorsByPhase_WhenPreDepartureFactorCollectorReturnsReports_ThenAddThemToList() {
            // Given
            PrecisionReport preDepartureReport = mock(PrecisionReport.class, RETURNS_DEEP_STUBS);
            given(preDepartureReport.getId().name()).willReturn("preDeparture");
            given(preDepartureFactorCollector.collect(anyString())).willReturn(List.of(preDepartureReport));


            //When
            Map<String, PrecisionReport> actualResult = statisticFactorService.getFactorsByPhase("AAAA");

            //Then
            Map<String, PrecisionReport> expectedMap = Map.of(preDepartureReport.getId().name(), preDepartureReport);

            then(actualResult).isEqualTo(expectedMap);
        }

        @Test
        @DisplayName("from additional time factor collector")
        void GetFactorsByPhase_WhenAdditionalTimeFactorCollectorReturnsReports_ThenAddThemToList() {
            // Given
            PrecisionReport additionalTimeReport = mock(PrecisionReport.class, RETURNS_DEEP_STUBS);
            given(additionalTimeReport.getId().name()).willReturn("additionalTime");
            given(additionalTimeFactorCollector.collect(anyString())).willReturn(List.of(additionalTimeReport));

            //When
            Map<String, PrecisionReport> actualResult = statisticFactorService.getFactorsByPhase("AAAA");

            //Then
            Map<String, PrecisionReport> expectedMap =
                    Map.of(additionalTimeReport.getId().name(), additionalTimeReport);

            then(actualResult).isEqualTo(expectedMap);
        }

        @Test
        @DisplayName("from arrival delay factor collector")
        void GetFactorsByPhase_WhenArrivalDelayFactorCollectorReturnsReports_ThenAddThemToList() {
            // Given
            PrecisionReport arrivalDelayReport = mock(PrecisionReport.class, RETURNS_DEEP_STUBS);
            given(arrivalDelayReport.getId().name()).willReturn("arrivalDelay");
            given(arrivalDelayFactorCollector.collect(anyString())).willReturn(List.of(arrivalDelayReport));

            //When
            Map<String, PrecisionReport> actualResult = statisticFactorService.getFactorsByPhase("AAAA");

            //Then
            Map<String, PrecisionReport> expectedMap =
                    Map.of(arrivalDelayReport.getId().name(), arrivalDelayReport);

            then(actualResult).isEqualTo(expectedMap);
        }

        @Test
        @DisplayName("from traffic factor collector")
        void GetFactorsByPhase_WhenTrafficFactorCollectorReturnsReports_ThenAddThemToList() {
            // Given
            PrecisionReport trafficReport = mock(PrecisionReport.class, RETURNS_DEEP_STUBS);
            given(trafficReport.getId().name()).willReturn("traffic");
            given(trafficFactorCollector.collect(anyString())).willReturn(List.of(trafficReport));

            //When
            Map<String, PrecisionReport> actualResult = statisticFactorService.getFactorsByPhase("AAAA");

            //Then
            Map<String, PrecisionReport> expectedMap =
                    Map.of(trafficReport.getId().name(), trafficReport);

            then(actualResult).isEqualTo(expectedMap);
        }
    }
}