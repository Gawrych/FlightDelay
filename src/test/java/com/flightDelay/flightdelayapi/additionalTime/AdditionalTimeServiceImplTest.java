package com.flightDelay.flightdelayapi.additionalTime;

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
@DisplayName("The additional time service")
class AdditionalTimeServiceImplTest {

    @Mock
    private AdditionalTimeRepository additionalTimeRepository;

    @Mock
    private AirportService airportService;

    @Mock
    private AdditionalTimeDtoMapper mapper;

    @Mock
    private EntityMapper entityMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private AdditionalTimeServiceImpl additionalTimeService;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Captor
    private ArgumentCaptor<AdditionalTime> additionalTimeCaptor;

    @Captor
    private ArgumentCaptor<List<AdditionalTime>> additionalTimeListCaptor;

    @Nested
    @DisplayName("passes data to")
    class PassDataTo {

        @Test
        @DisplayName("additional time repository to get additional time records")
        void FindAllLatestByAirport_WhenPassJsonInputToMapper_ThenReturnDeserializeJson() {
            // Given
            String airportCode = "AAAA";

            // When
            additionalTimeService.findAllLatestByAirport(airportCode);

            // Then
            verify(additionalTimeRepository).findAllByAirportWithDateAfter(stringCaptor.capture(), any(LocalDate.class));

            then(stringCaptor.getValue()).isEqualTo(airportCode);
        }

        @Test
        @DisplayName("additional time mapper to map additional time to dto")
        void FindAllLatestByAirport_WhenPassRecordListToMapper_ThenRecordDtoList() {
            // Given
            List<AdditionalTime> list = List.of(mock(AdditionalTime.class));

            given(additionalTimeRepository.findAllByAirportWithDateAfter(anyString(), any(LocalDate.class)))
                    .willReturn(list);

            // When
            additionalTimeService.findAllLatestByAirport("");

            // Then
            verify(mapper).mapFromList(additionalTimeListCaptor.capture());

            then(additionalTimeListCaptor.getValue()).isEqualTo(list);
        }

        @Test
        @DisplayName("entity mapper to deserialize json")
        void UpdateFromJson_WhenPassJsonInputToMapper_ThenReturnDeserializeJson() {
            // Given
            given(entityMapper.jsonArrayToList(anyString(), same(AdditionalTime.class), same(objectMapper)))
                    .willReturn(List.of());

            // When
            additionalTimeService.updateFromJson("");

            // Then
            verify(entityMapper).jsonArrayToList(stringCaptor.capture(), same(AdditionalTime.class), same(objectMapper));
        }

        @Test
        @DisplayName("additional time repository to save additional time record in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordToRepositoryToSave() {
            // Given
            AdditionalTime additionalTime = mock(AdditionalTime.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(additionalTime.getAirportCode()).willReturn("AAAA");
            given(additionalTime.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(additionalTimeRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getAdditionalTimes().add(any(AdditionalTime.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            given(entityMapper.jsonArrayToList(anyString(), same(AdditionalTime.class), same(objectMapper)))
                    .willReturn(List.of(additionalTime));

            // When
            additionalTimeService.updateFromJson("");

            // Then
            verify(additionalTimeRepository).save(additionalTimeCaptor.capture());

            then(additionalTimeCaptor.getValue()).isEqualTo(additionalTime);
        }

        @Test
        @DisplayName("additional time repository to save additional time records list in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordListToRepositoryToSave() {
            // Given
            AdditionalTime additionalTime = mock(AdditionalTime.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(additionalTime.getAirportCode()).willReturn("AAAA");
            given(additionalTime.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(additionalTimeRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getAdditionalTimes().add(any(AdditionalTime.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            additionalTimeService.updateFromJson(List.of(additionalTime));

            // Then
            verify(additionalTimeRepository).save(additionalTimeCaptor.capture());

            then(additionalTimeCaptor.getValue()).isEqualTo(additionalTime);
        }
    }

    @Nested
    @DisplayName("returns entities added to the database")
    class ReturnOnlyAdded {

        @Test
        @DisplayName("when passes json")
        void UpdateFromJson_WhenPassJson_ThenReturnsOnlyAddedEntities() {
            // Given
            AdditionalTime correctAdditionalTime = mock(AdditionalTime.class);
            AdditionalTime duplicatedAdditionalTime = mock(AdditionalTime.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(correctAdditionalTime.getAirportCode()).willReturn("AAAA");
            given(correctAdditionalTime.generateId()).willReturn("The same id");

            given(duplicatedAdditionalTime.getAirportCode()).willReturn("BBBB");
            given(duplicatedAdditionalTime.generateId()).willReturn("The same id");

            given(airportService.existsByAirportIdent(same(correctAdditionalTime.getAirportCode()))).willReturn(true);
            given(airportService.existsByAirportIdent(same(duplicatedAdditionalTime.getAirportCode()))).willReturn(false);
            given(additionalTimeRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getAdditionalTimes().add(any(AdditionalTime.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            given(entityMapper.jsonArrayToList(anyString(), same(AdditionalTime.class), same(objectMapper)))
                    .willReturn(List.of(correctAdditionalTime, duplicatedAdditionalTime));

            // When
            List<AdditionalTime> actualAdded = additionalTimeService.updateFromJson("");

            // Then
            then(actualAdded).isEqualTo(List.of(correctAdditionalTime));
        }

        @Test
        @DisplayName("when passes additional time records list")
        void UpdateFromJson_WhenPassRecordList_ThenReturnsOnlyAddedEntities() {
            // Given
            AdditionalTime correctAdditionalTime = mock(AdditionalTime.class);
            AdditionalTime duplicatedAdditionalTime = mock(AdditionalTime.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(correctAdditionalTime.getAirportCode()).willReturn("AAAA");
            given(correctAdditionalTime.generateId()).willReturn("The same id");

            given(duplicatedAdditionalTime.getAirportCode()).willReturn("BBBB");
            given(duplicatedAdditionalTime.generateId()).willReturn("The same id");

            given(airportService.existsByAirportIdent(same(correctAdditionalTime.getAirportCode()))).willReturn(true);
            given(airportService.existsByAirportIdent(same(duplicatedAdditionalTime.getAirportCode()))).willReturn(false);
            given(additionalTimeRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getAdditionalTimes().add(any(AdditionalTime.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            List<AdditionalTime> actualAdded = additionalTimeService.updateFromJson(List.of(correctAdditionalTime, duplicatedAdditionalTime));

            // Then
            then(actualAdded).isEqualTo(List.of(correctAdditionalTime));
        }
    }

    @Nested
    @DisplayName("save in database")
    class SaveInDatabase {

        @Test
        @DisplayName("when assigned airport exists and new additional time record does not exists in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordListToRepositoryToSave() {
            // Given
            AdditionalTime additionalTime = mock(AdditionalTime.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(additionalTime.getAirportCode()).willReturn("AAAA");
            given(additionalTime.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(additionalTimeRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getAdditionalTimes().add(any(AdditionalTime.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            boolean saved = additionalTimeService.save(additionalTime);

            // Then

            then(saved).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("does not save new record in database")
    class NotSaveInDatabase {

        @Test
        @DisplayName("when airport assigned to new additional time record not exists in database")
        void CalculateTopMonthDelay_WhenAirportFromAdditionalTimeRecordNotExists_ThenReturnFalse() {
            // Given
            AdditionalTime additionalTime = mock(AdditionalTime.class);
            given(additionalTime.getAirportCode()).willReturn("AAAA");

            given(airportService.existsByAirportIdent(anyString())).willReturn(false);

            // When
            boolean saved = additionalTimeService.save(additionalTime);

            // Then
            then(saved).isEqualTo(false);
        }

        @Test
        @DisplayName("when new additional time record already exists in database")
        void CalculateTopMonthDelay_WhenNewAdditionalTimeRecordAlreadyExists_ThenReturnFalse() {
            // Given
            AdditionalTime additionalTime = mock(AdditionalTime.class);
            given(additionalTime.getAirportCode()).willReturn("AAAA");
            given(additionalTime.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(additionalTimeRepository.existsByGeneratedId(anyString())).willReturn(true);

            // When
            boolean saved = additionalTimeService.save(additionalTime);

            // Then
            then(saved).isEqualTo(false);
        }
    }
}