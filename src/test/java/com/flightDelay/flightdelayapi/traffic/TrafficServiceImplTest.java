package com.flightDelay.flightdelayapi.traffic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.shared.mapper.EntityMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("The traffic service")
class TrafficServiceImplTest {

    @Mock
    private TrafficRepository trafficRepository;

    @Mock
    private AirportService airportService;

    @Mock
    private TrafficDtoMapper mapper;

    @Mock
    private EntityMapper entityMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TrafficServiceImpl trafficService;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Captor
    private ArgumentCaptor<Traffic> trafficCaptor;

    @Captor
    private ArgumentCaptor<List<Traffic>> trafficListCaptor;

    @Nested
    @DisplayName("passes data to")
    class PassDataTo {

        @Test
        @DisplayName("traffic repository to get traffic records")
        void FindAllLatestByAirport_WhenPassJsonInputToMapper_ThenReturnDeserializeJson() {
            // Given
            String airportCode = "AAAA";

            // When
            trafficService.findAllLatestByAirport(airportCode);

            // Then
            verify(trafficRepository).findAllByAirportWithDateAfter(stringCaptor.capture(), any(LocalDate.class));

            then(stringCaptor.getValue()).isEqualTo(airportCode);
        }

        @Test
        @DisplayName("traffic mapper to map traffic to dto")
        void FindAllLatestByAirport_WhenPassTrafficListToMapper_ThenTrafficDtoList() {
            // Given
            List<Traffic> trafficList = List.of(mock(Traffic.class));

            given(trafficRepository.findAllByAirportWithDateAfter(anyString(), any(LocalDate.class)))
                    .willReturn(trafficList);

            // When
            trafficService.findAllLatestByAirport("");

            // Then
            verify(mapper).mapFromList(trafficListCaptor.capture());

            then(trafficListCaptor.getValue()).isEqualTo(trafficList);
        }

        @Test
        @DisplayName("entity mapper to deserialize json")
        void UpdateFromJson_WhenPassJsonInputToMapper_ThenReturnDeserializeJson() {
            // Given
            given(entityMapper.jsonArrayToList(anyString(), same(Traffic.class), same(objectMapper)))
                    .willReturn(List.of());

            // When
            trafficService.updateFromJson("");

            // Then
            verify(entityMapper).jsonArrayToList(stringCaptor.capture(), same(Traffic.class), same(objectMapper));
        }

        @Test
        @DisplayName("traffic repository to save traffic in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassTrafficToRepositoryToSave() {
            // Given
            Traffic traffic = mock(Traffic.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(traffic.getAirportCode()).willReturn("AAAA");
            given(traffic.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(trafficRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getTrafficReports().add(any(Traffic.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            given(entityMapper.jsonArrayToList(anyString(), same(Traffic.class), same(objectMapper)))
                    .willReturn(List.of(traffic));

            // When
            trafficService.updateFromJson("");

            // Then
            verify(trafficRepository).save(trafficCaptor.capture());

            then(trafficCaptor.getValue()).isEqualTo(traffic);
        }

        @Test
        @DisplayName("traffic repository to save traffic list in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassTrafficListToRepositoryToSave() {
            // Given
            Traffic traffic = mock(Traffic.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(traffic.getAirportCode()).willReturn("AAAA");
            given(traffic.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(trafficRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getTrafficReports().add(any(Traffic.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            trafficService.updateFromJson(List.of(traffic));

            // Then
            verify(trafficRepository).save(trafficCaptor.capture());

            then(trafficCaptor.getValue()).isEqualTo(traffic);
        }
    }

    @Nested
    @DisplayName("returns entities added to the database")
    class ReturnOnlyAdded {

        @Test
        @DisplayName("when passes json")
        void UpdateFromJson_WhenPassJson_ThenReturnsOnlyAddedEntities() {
            // Given
            Traffic correctTraffic = mock(Traffic.class);
            Traffic duplicatedTraffic = mock(Traffic.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(correctTraffic.getAirportCode()).willReturn("AAAA");
            given(correctTraffic.generateId()).willReturn("The same id");

            given(duplicatedTraffic.getAirportCode()).willReturn("BBBB");
            given(duplicatedTraffic.generateId()).willReturn("The same id");

            given(airportService.existsByAirportIdent(same(correctTraffic.getAirportCode()))).willReturn(true);
            given(airportService.existsByAirportIdent(same(duplicatedTraffic.getAirportCode()))).willReturn(false);
            given(trafficRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getTrafficReports().add(any(Traffic.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            given(entityMapper.jsonArrayToList(anyString(), same(Traffic.class), same(objectMapper)))
                    .willReturn(List.of(correctTraffic, duplicatedTraffic));

            // When
            List<Traffic> actualAdded = trafficService.updateFromJson("");

            // Then
            then(actualAdded).isEqualTo(List.of(correctTraffic));
        }

        @Test
        @DisplayName("when passes traffic list")
        void UpdateFromJson_WhenPassTrafficList_ThenReturnsOnlyAddedEntities() {
            // Given
            Traffic correctTraffic = mock(Traffic.class);
            Traffic duplicatedTraffic = mock(Traffic.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(correctTraffic.getAirportCode()).willReturn("AAAA");
            given(correctTraffic.generateId()).willReturn("The same id");

            given(duplicatedTraffic.getAirportCode()).willReturn("BBBB");
            given(duplicatedTraffic.generateId()).willReturn("The same id");

            given(airportService.existsByAirportIdent(same(correctTraffic.getAirportCode()))).willReturn(true);
            given(airportService.existsByAirportIdent(same(duplicatedTraffic.getAirportCode()))).willReturn(false);
            given(trafficRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getTrafficReports().add(any(Traffic.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            List<Traffic> actualAdded = trafficService.updateFromJson(List.of(correctTraffic, duplicatedTraffic));

            // Then
            then(actualAdded).isEqualTo(List.of(correctTraffic));
        }
    }

    @Nested
    @DisplayName("save in database")
    class SaveInDatabase {

        @Test
        @DisplayName("when assigned airport exists and new traffic does not exists in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassTrafficListToRepositoryToSave() {
            // Given
            Traffic traffic = mock(Traffic.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(traffic.getAirportCode()).willReturn("AAAA");
            given(traffic.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(trafficRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getTrafficReports().add(any(Traffic.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            boolean saved = trafficService.save(traffic);

            // Then

            then(saved).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("does not save new record in database")
    class NotSaveInDatabase {

        @Test
        @DisplayName("when airport assigned to new traffic not exists in database")
        void CalculateTopMonthDelay_WhenAirportFromTrafficNotExists_ThenReturnFalse() {
            // Given
            Traffic traffic = mock(Traffic.class);
            given(traffic.getAirportCode()).willReturn("AAAA");

            given(airportService.existsByAirportIdent(anyString())).willReturn(false);

            // When
            boolean saved = trafficService.save(traffic);

            // Then
            then(saved).isEqualTo(false);
        }

        @Test
        @DisplayName("when new traffic already exists in database")
        void CalculateTopMonthDelay_WhenNewTrafficAlreadyExists_ThenReturnFalse() {
            // Given
            Traffic traffic = mock(Traffic.class);
            given(traffic.getAirportCode()).willReturn("AAAA");
            given(traffic.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(trafficRepository.existsByGeneratedId(anyString())).willReturn(true);

            // When
            boolean saved = trafficService.save(traffic);

            // Then
            then(saved).isEqualTo(false);
        }
    }
}
