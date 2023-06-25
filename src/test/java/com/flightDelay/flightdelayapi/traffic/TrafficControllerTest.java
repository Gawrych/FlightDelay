package com.flightDelay.flightdelayapi.traffic;

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
@DisplayName("The traffic controller")
class TrafficControllerTest {

    private MockMvc mvc;

    @Mock
    private TrafficServiceImpl trafficService;

    @Mock
    private DataImportService dataImportService;

    @InjectMocks
    private TrafficController trafficController;

    private JacksonTester<List<Traffic>> jsonSerializer;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        this.mvc = MockMvcBuilders.standaloneSetup(trafficController).build();
    }

    @Nested
    @DisplayName("returns all entities added to the database")
    class ReturnsAllAddedEntities {
        @Test
        @DisplayName("from file")
        void UpdateFromFile_WhenAddEntitiesFromFileToDatabase_ThenReturnAddedEntities() throws Exception {
            // Given
            given(dataImportService.importFromFile(same(trafficService), anyString())).willReturn(List.of());

            ReflectionTestUtils.setField(trafficController, "trafficConverterName", "");

            // When
            mvc.perform(put("/api/v1/traffic/file"))
                    .andReturn()
                    .getResponse();

            // Then
            verify(dataImportService).importFromFile(same(trafficService), anyString());
        }

        @Test
        @DisplayName("from json")
        void Update_WhenAddEntitiesFromJsonToDatabase_ThenReturnAddedEntities() throws Exception {
            // Given
            Traffic expectedValue = new Traffic();
            given(trafficService.updateFromJson(anyList())).willReturn(List.of(expectedValue));

            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/traffic")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("[{}]"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getContentAsString()).isEqualTo(jsonSerializer.write(List.of(expectedValue)).getJson());
            verify(trafficService).updateFromJson(anyList());
        }
    }

    @Nested
    @DisplayName("returns CREATED status after added entities to database")
    class StatusCode {

        @Test
        @DisplayName("from file")
        void UpdateFromFile_WhenAddedEntitiesFromFile_ThenReturnCreatedHttpsStatus() throws Exception {
            // Given
            given(dataImportService.importFromFile(same(trafficService), anyString())).willReturn(List.of());

            ReflectionTestUtils.setField(trafficController, "trafficConverterName", "");

            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/traffic/file"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        }

        @Test
        @DisplayName("from json")
        void Update_WhenAddedEntitiesFromJson_ThenReturnCreatedHttpsStatus() throws Exception {
            // Given
            given(trafficService.updateFromJson(anyList())).willReturn(List.of());

            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/traffic")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("[{}]"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        }
    }
}