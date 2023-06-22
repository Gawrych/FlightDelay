package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.shared.dataImport.DataImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
@DisplayName("The pre departure delay controller")
class PreDepartureDelayControllerTest {

    private MockMvc mvc;

    @Mock
    private PreDepartureDelayServiceImpl preDepartureDelayService;

    @Mock
    private DataImportService dataImportService;

    @InjectMocks
    private PreDepartureDelayController preDepartureDelayController;

    private JacksonTester<List<PreDepartureDelay>> jsonSerializer;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        this.mvc = MockMvcBuilders.standaloneSetup(preDepartureDelayController).build();
    }

    @Nested
    @DisplayName("returns all entities added to the database")
    class ReturnsAllAddedEntities {

        @Test
        @DisplayName("from file")
        void UpdateFromFile_WhenAddEntitiesFromFileToDatabase_ThenReturnAddedEntities() throws Exception {
            // Given
            given(dataImportService.importFromFile(same(preDepartureDelayService), anyString())).willReturn(List.of());

            ReflectionTestUtils.setField(preDepartureDelayController, "preDepartureDelayConverterName", "");


            // When
            mvc.perform(put("/api/v1/pre-departure/file"))
                    .andReturn()
                    .getResponse();

            // Then
            verify(dataImportService).importFromFile(same(preDepartureDelayService), anyString());
        }

        @Test
        @DisplayName("from json")
        void UpdateFromFile_WhenAddEntitiesFromJsonToDatabase_ThenReturnAddedEntities() throws Exception {
            // Given
            PreDepartureDelay expectedValue = new PreDepartureDelay();
            given(preDepartureDelayService.updateFromJson(anyList())).willReturn(List.of(expectedValue));

            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/pre-departure")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("[{}]"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getContentAsString()).isEqualTo(jsonSerializer.write(List.of(expectedValue)).getJson());
            verify(preDepartureDelayService).updateFromJson(anyList());
        }
    }

    @Nested
    @DisplayName("returns CREATED status after added entities to database")
    class StatusCode {

        @Test
        @DisplayName("from file")
        void UpdateFromFile_WhenAddedEntitiesFromFile_ThenReturnCreatedHttpsStatus() throws Exception {
            // Given
            given(dataImportService.importFromFile(same(preDepartureDelayService), anyString())).willReturn(List.of());

            ReflectionTestUtils.setField(preDepartureDelayController, "preDepartureDelayConverterName", "");


            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/pre-departure/file"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        }

        @Test
        @DisplayName("from json")
        void UpdateFromFile_WhenAddedEntitiesFromJson_ThenReturnCreatedHttpsStatus() throws Exception {
            // Given
            given(preDepartureDelayService.updateFromJson(anyList())).willReturn(List.of());

            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/pre-departure")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("[{}]"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        }
    }
}