package com.flightDelay.flightdelayapi.preDepartureDelay;

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
@DisplayName("The pre departure delay service")
class PreDepartureDelayServiceImplTest {

    @Mock
    private PreDepartureDelayRepository preDepartureDelayRepository;

    @Mock
    private AirportService airportService;

    @Mock
    private PreDepartureDelayDtoMapper mapper;

    @Mock
    private EntityMapper entityMapper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PreDepartureDelayServiceImpl preDepartureDelayService;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Captor
    private ArgumentCaptor<PreDepartureDelay> preDepartureDelayCaptor;

    @Captor
    private ArgumentCaptor<List<PreDepartureDelay>> preDepartureDelayListCaptor;

    @Nested
    @DisplayName("passes data to")
    class PassDataTo {

        @Test
        @DisplayName("pre departure delay repository to get additional time records")
        void FindAllLatestByAirport_WhenPassJsonInputToMapper_ThenReturnDeserializeJson() {
            // Given
            String airportCode = "AAAA";

            // When
            preDepartureDelayService.findAllLatestByAirport(airportCode);

            // Then
            verify(preDepartureDelayRepository).findAllByAirportWithDateAfter(stringCaptor.capture(), any(LocalDate.class));

            then(stringCaptor.getValue()).isEqualTo(airportCode);
        }

        @Test
        @DisplayName("pre departure delay mapper to map additional time to dto")
        void FindAllLatestByAirport_WhenPassRecordListToMapper_ThenRecordDtoList() {
            // Given
            List<PreDepartureDelay> list = List.of(mock(PreDepartureDelay.class));

            given(preDepartureDelayRepository.findAllByAirportWithDateAfter(anyString(), any(LocalDate.class)))
                    .willReturn(list);

            // When
            preDepartureDelayService.findAllLatestByAirport("");

            // Then
            verify(mapper).mapFromList(preDepartureDelayListCaptor.capture());

            then(preDepartureDelayListCaptor.getValue()).isEqualTo(list);
        }

        @Test
        @DisplayName("entity mapper to deserialize json")
        void UpdateFromJson_WhenPassJsonInputToMapper_ThenReturnDeserializeJson() {
            // Given
            given(entityMapper.jsonArrayToList(anyString(), same(PreDepartureDelay.class), same(objectMapper)))
                    .willReturn(List.of());

            // When
            preDepartureDelayService.updateFromJson("");

            // Then
            verify(entityMapper).jsonArrayToList(stringCaptor.capture(), same(PreDepartureDelay.class), same(objectMapper));
        }

        @Test
        @DisplayName("pre departure delay repository to save additional time record in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordToRepositoryToSave() {
            // Given
            PreDepartureDelay preDepartureDelay = mock(PreDepartureDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(preDepartureDelay.getAirportCode()).willReturn("AAAA");
            given(preDepartureDelay.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(preDepartureDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getPreDepartureDelays().add(any(PreDepartureDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            given(entityMapper.jsonArrayToList(anyString(), same(PreDepartureDelay.class), same(objectMapper)))
                    .willReturn(List.of(preDepartureDelay));

            // When
            preDepartureDelayService.updateFromJson("");

            // Then
            verify(preDepartureDelayRepository).save(preDepartureDelayCaptor.capture());

            then(preDepartureDelayCaptor.getValue()).isEqualTo(preDepartureDelay);
        }

        @Test
        @DisplayName("pre departure delay repository to save additional time records list in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordListToRepositoryToSave() {
            // Given
            PreDepartureDelay preDepartureDelay = mock(PreDepartureDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(preDepartureDelay.getAirportCode()).willReturn("AAAA");
            given(preDepartureDelay.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(preDepartureDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getPreDepartureDelays().add(any(PreDepartureDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            preDepartureDelayService.updateFromJson(List.of(preDepartureDelay));

            // Then
            verify(preDepartureDelayRepository).save(preDepartureDelayCaptor.capture());

            then(preDepartureDelayCaptor.getValue()).isEqualTo(preDepartureDelay);
        }
    }

    @Nested
    @DisplayName("returns entities added to the database")
    class ReturnOnlyAdded {

        @Test
        @DisplayName("when passes json")
        void UpdateFromJson_WhenPassJson_ThenReturnsOnlyAddedEntities() {
            // Given
            PreDepartureDelay correctPreDepartureDelay = mock(PreDepartureDelay.class);
            PreDepartureDelay duplicatedPreDepartureDelay = mock(PreDepartureDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(correctPreDepartureDelay.getAirportCode()).willReturn("AAAA");
            given(correctPreDepartureDelay.generateId()).willReturn("The same id");

            given(duplicatedPreDepartureDelay.getAirportCode()).willReturn("BBBB");
            given(duplicatedPreDepartureDelay.generateId()).willReturn("The same id");

            given(airportService.existsByAirportIdent(same(correctPreDepartureDelay.getAirportCode()))).willReturn(true);
            given(airportService.existsByAirportIdent(same(duplicatedPreDepartureDelay.getAirportCode()))).willReturn(false);
            given(preDepartureDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getPreDepartureDelays().add(any(PreDepartureDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            given(entityMapper.jsonArrayToList(anyString(), same(PreDepartureDelay.class), same(objectMapper)))
                    .willReturn(List.of(correctPreDepartureDelay, duplicatedPreDepartureDelay));

            // When
            List<PreDepartureDelay> actualAdded = preDepartureDelayService.updateFromJson("");

            // Then
            then(actualAdded).isEqualTo(List.of(correctPreDepartureDelay));
        }

        @Test
        @DisplayName("when passes pre departure delay records list")
        void UpdateFromJson_WhenPassRecordList_ThenReturnsOnlyAddedEntities() {
            // Given
            PreDepartureDelay correctPreDepartureDelay = mock(PreDepartureDelay.class);
            PreDepartureDelay duplicatedPreDepartureDelay = mock(PreDepartureDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(correctPreDepartureDelay.getAirportCode()).willReturn("AAAA");
            given(correctPreDepartureDelay.generateId()).willReturn("The same id");

            given(duplicatedPreDepartureDelay.getAirportCode()).willReturn("BBBB");
            given(duplicatedPreDepartureDelay.generateId()).willReturn("The same id");

            given(airportService.existsByAirportIdent(same(correctPreDepartureDelay.getAirportCode()))).willReturn(true);
            given(airportService.existsByAirportIdent(same(duplicatedPreDepartureDelay.getAirportCode()))).willReturn(false);
            given(preDepartureDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getPreDepartureDelays().add(any(PreDepartureDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            List<PreDepartureDelay> actualAdded = preDepartureDelayService.updateFromJson(List.of(correctPreDepartureDelay, duplicatedPreDepartureDelay));

            // Then
            then(actualAdded).isEqualTo(List.of(correctPreDepartureDelay));
        }
    }

    @Nested
    @DisplayName("save in database")
    class SaveInDatabase {

        @Test
        @DisplayName("when assigned airport exists and new pre departure delay record does not exists in database")
        void UpdateFromJson_WhenReturnDataFromMapper_ThenPassRecordListToRepositoryToSave() {
            // Given
            PreDepartureDelay preDepartureDelay = mock(PreDepartureDelay.class);
            Airport airport = mock(Airport.class, RETURNS_DEEP_STUBS);

            given(preDepartureDelay.getAirportCode()).willReturn("AAAA");
            given(preDepartureDelay.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(preDepartureDelayRepository.existsByGeneratedId(anyString())).willReturn(false);

            given(airport.getPreDepartureDelays().add(any(PreDepartureDelay.class))).willReturn(true);
            given(airportService.findByAirportIdent(anyString())).willReturn(airport);

            // When
            boolean saved = preDepartureDelayService.save(preDepartureDelay);

            // Then

            then(saved).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("does not save new record in database")
    class NotSaveInDatabase {

        @Test
        @DisplayName("when airport assigned to new pre departure delay record not exists in database")
        void CalculateTopMonthDelay_WhenAirportFromAdditionalTimeRecordNotExists_ThenReturnFalse() {
            // Given
            PreDepartureDelay preDepartureDelay = mock(PreDepartureDelay.class);
            given(preDepartureDelay.getAirportCode()).willReturn("AAAA");

            given(airportService.existsByAirportIdent(anyString())).willReturn(false);

            // When
            boolean saved = preDepartureDelayService.save(preDepartureDelay);

            // Then
            then(saved).isEqualTo(false);
        }

        @Test
        @DisplayName("when new pre departure delay record already exists in database")
        void CalculateTopMonthDelay_WhenNewAdditionalTimeRecordAlreadyExists_ThenReturnFalse() {
            // Given
            PreDepartureDelay preDepartureDelay = mock(PreDepartureDelay.class);
            given(preDepartureDelay.getAirportCode()).willReturn("AAAA");
            given(preDepartureDelay.generateId()).willReturn("ID");

            given(airportService.existsByAirportIdent(anyString())).willReturn(true);
            given(preDepartureDelayRepository.existsByGeneratedId(anyString())).willReturn(true);

            // When
            boolean saved = preDepartureDelayService.save(preDepartureDelay);

            // Then
            then(saved).isEqualTo(false);
        }
    }
}