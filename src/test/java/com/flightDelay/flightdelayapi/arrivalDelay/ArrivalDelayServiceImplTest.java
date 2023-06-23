package com.flightDelay.flightdelayapi.arrivalDelay;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@DisplayName("The arrival delay service")
class ArrivalDelayServiceImplTest {

    @Mock
    private ArrivalDelayRepository arrivalDelayRepository;

    @Mock
    private AirportService airportService;

    @Mock
    private ArrivalDelayDtoMapper mapper;

    @Mock
    private EntityMapper entityMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ArrivalDelayServiceImpl arrivalDelayService;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Captor
    private ArgumentCaptor<ArrivalDelay> arrivalDelayTimeCaptor;

    @Captor
    private ArgumentCaptor<List<ArrivalDelay>> arrivalDelayTimeListCaptor;

    @Nested
    @DisplayName("passes data to")
    class PassDataTo {

        @Test
        @DisplayName("arrival delay repository to get additional time records")
        void FindAllLatestByAirport_WhenPassJsonInputToMapper_ThenReturnDeserializeJson() {
            // Given
            String airportCode = "AAAA";

            // When
            arrivalDelayService.findAllLatestByAirport(airportCode);

            // Then
            verify(arrivalDelayRepository).findAllByAirportWithDateAfter(stringCaptor.capture(), any(LocalDate.class));

            then(stringCaptor.getValue()).isEqualTo(airportCode);
        }

        @Test
        @DisplayName("arrival delay mapper to map additional time to dto")
        void FindAllLatestByAirport_WhenPassRecordListToMapper_ThenRecordDtoList() {
            // Given
            List<ArrivalDelay> list = List.of(mock(ArrivalDelay.class));

            given(arrivalDelayRepository.findAllByAirportWithDateAfter(anyString(), any(LocalDate.class)))
                    .willReturn(list);

            // When
            arrivalDelayService.findAllLatestByAirport("");

            // Then
            verify(mapper).mapFromList(arrivalDelayTimeListCaptor.capture());

            then(arrivalDelayTimeListCaptor.getValue()).isEqualTo(list);
        }

        @Test
        @DisplayName("entity mapper to deserialize json")
        void UpdateFromJson_WhenPassJsonInputToMapper_ThenReturnDeserializeJson() {
            // Given
            given(entityMapper.jsonArrayToList(anyString(), same(ArrivalDelay.class), same(objectMapper)))
                    .willReturn(List.of());

            // When
            arrivalDelayService.updateFromJson("");

            // Then
            verify(entityMapper).jsonArrayToList(stringCaptor.capture(), same(ArrivalDelay.class), same(objectMapper));
        }

        @Test
        @DisplayName("arrival delay repository to save additional time record in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordToRepositoryToSave() {
            // Given
            ArrivalDelay arrivalDelay = mock(ArrivalDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(arrivalDelay.getAirportCode()).willReturn("AAAA");
            given(arrivalDelay.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(arrivalDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getArrivalDelays().add(any(ArrivalDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            given(entityMapper.jsonArrayToList(anyString(), same(ArrivalDelay.class), same(objectMapper)))
                    .willReturn(List.of(arrivalDelay));

            // When
            arrivalDelayService.updateFromJson("");

            // Then
            verify(arrivalDelayRepository).save(arrivalDelayTimeCaptor.capture());

            then(arrivalDelayTimeCaptor.getValue()).isEqualTo(arrivalDelay);
        }

        @Test
        @DisplayName("arrival delay repository to save additional time records list in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordListToRepositoryToSave() {
            // Given
            ArrivalDelay arrivalDelay = mock(ArrivalDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(arrivalDelay.getAirportCode()).willReturn("AAAA");
            given(arrivalDelay.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(arrivalDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getArrivalDelays().add(any(ArrivalDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            arrivalDelayService.updateFromJson(List.of(arrivalDelay));

            // Then
            verify(arrivalDelayRepository).save(arrivalDelayTimeCaptor.capture());

            then(arrivalDelayTimeCaptor.getValue()).isEqualTo(arrivalDelay);
        }
    }

    @Nested
    @DisplayName("returns entities added to the database")
    class ReturnOnlyAdded {

        @Test
        @DisplayName("when passes json")
        void UpdateFromJson_WhenPassJson_ThenReturnsOnlyAddedEntities() {
            // Given
            ArrivalDelay correctArrivalDelay = mock(ArrivalDelay.class);
            ArrivalDelay duplicatedArrivalDelay = mock(ArrivalDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(correctArrivalDelay.getAirportCode()).willReturn("AAAA");
            given(correctArrivalDelay.generateId()).willReturn("The same id");

            given(duplicatedArrivalDelay.getAirportCode()).willReturn("BBBB");
            given(duplicatedArrivalDelay.generateId()).willReturn("The same id");

            given(airportService.existsByAirportIdent(same(correctArrivalDelay.getAirportCode()))).willReturn(true);
            given(airportService.existsByAirportIdent(same(duplicatedArrivalDelay.getAirportCode()))).willReturn(false);
            given(arrivalDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getArrivalDelays().add(any(ArrivalDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            given(entityMapper.jsonArrayToList(anyString(), same(ArrivalDelay.class), same(objectMapper)))
                    .willReturn(List.of(correctArrivalDelay, duplicatedArrivalDelay));

            // When
            List<ArrivalDelay> actualAdded = arrivalDelayService.updateFromJson("");

            // Then
            then(actualAdded).isEqualTo(List.of(correctArrivalDelay));
        }

        @Test
        @DisplayName("when passes arrival delay records list")
        void UpdateFromJson_WhenPassRecordList_ThenReturnsOnlyAddedEntities() {
            // Given
            ArrivalDelay correctArrivalDelay = mock(ArrivalDelay.class);
            ArrivalDelay duplicatedArrivalDelay = mock(ArrivalDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(correctArrivalDelay.getAirportCode()).willReturn("AAAA");
            given(correctArrivalDelay.generateId()).willReturn("The same id");

            given(duplicatedArrivalDelay.getAirportCode()).willReturn("BBBB");
            given(duplicatedArrivalDelay.generateId()).willReturn("The same id");

            given(airportService.existsByAirportIdent(same(correctArrivalDelay.getAirportCode()))).willReturn(true);
            given(airportService.existsByAirportIdent(same(duplicatedArrivalDelay.getAirportCode()))).willReturn(false);
            given(arrivalDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getArrivalDelays().add(any(ArrivalDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            List<ArrivalDelay> actualAdded = arrivalDelayService.updateFromJson(List.of(correctArrivalDelay, duplicatedArrivalDelay));

            // Then
            then(actualAdded).isEqualTo(List.of(correctArrivalDelay));
        }
    }

    @Nested
    @DisplayName("save in database")
    class SaveInDatabase {

        @Test
        @DisplayName("when assigned airport exists and new arrival delay record does not exists in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordListToRepositoryToSave() {
            // Given
            ArrivalDelay arrivalDelay = mock(ArrivalDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(arrivalDelay.getAirportCode()).willReturn("AAAA");
            given(arrivalDelay.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(arrivalDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getArrivalDelays().add(any(ArrivalDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            boolean saved = arrivalDelayService.save(arrivalDelay);

            // Then

            then(saved).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("does not save new record in database")
    class NotSaveInDatabase {

        @Test
        @DisplayName("when airport assigned to new arrival delay record not exists in database")
        void CalculateTopMonthDelay_WhenAirportFromAdditionalTimeRecordNotExists_ThenReturnFalse() {
            // Given
            ArrivalDelay arrivalDelay = mock(ArrivalDelay.class);
            given(arrivalDelay.getAirportCode()).willReturn("AAAA");

            given(airportService.existsByAirportIdent(anyString())).willReturn(false);

            // When
            boolean saved = arrivalDelayService.save(arrivalDelay);

            // Then
            then(saved).isEqualTo(false);
        }

        @Test
        @DisplayName("when new arrival delay record already exists in database")
        void CalculateTopMonthDelay_WhenNewAdditionalTimeRecordAlreadyExists_ThenReturnFalse() {
            // Given
            ArrivalDelay arrivalDelay = mock(ArrivalDelay.class);
            given(arrivalDelay.getAirportCode()).willReturn("AAAA");
            given(arrivalDelay.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(arrivalDelayRepository.existsByGeneratedId(anyString())).willReturn(true);

            // When
            boolean saved = arrivalDelayService.save(arrivalDelay);

            // Then
            then(saved).isEqualTo(false);
        }
    }
}