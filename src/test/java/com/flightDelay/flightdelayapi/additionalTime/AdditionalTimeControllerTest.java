package com.flightDelay.flightdelayapi.additionalTime;

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
@DisplayName("The additional time controller")
class AdditionalTimeControllerTest {

    private MockMvc mvc;

    @Mock
    private AdditionalTimeService additionalTimeService;

    @Mock
    private DataImportService dataImportService;

    @InjectMocks
    private AdditionalTimeController additionalTimeController;

    private JacksonTester<List<AdditionalTime>> jsonSerializer;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        this.mvc = MockMvcBuilders.standaloneSetup(additionalTimeController).build();
    }

    @Nested
    @DisplayName("returns all entities added to the database")
    class ReturnsAllAddedEntities {

        @Test
        @DisplayName("from file")
        void UpdateFromFile_WhenAddEntitiesFromFileToDatabase_ThenReturnAddedEntities() throws Exception {
            // Given
            given(dataImportService.importFromFile(same(additionalTimeService), anyString())).willReturn(List.of());

            ReflectionTestUtils.setField(additionalTimeController, "additionalTimeConverterName", "");


            // When
            mvc.perform(put("/api/v1/additional-time/file"))
                    .andReturn()
                    .getResponse();

            // Then
            verify(dataImportService).importFromFile(same(additionalTimeService), anyString());
        }

        @Test
        @DisplayName("from json")
        void UpdateFromFile_WhenAddEntitiesFromJsonToDatabase_ThenReturnAddedEntities() throws Exception {
            // Given
            AdditionalTime expectedValue = new AdditionalTime();
            given(additionalTimeService.updateFromJson(anyList())).willReturn(List.of(expectedValue));

            ReflectionTestUtils.setField(additionalTimeController, "additionalTimeConverterName", "");

            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/additional-time")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("[{}]"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getContentAsString()).isEqualTo(jsonSerializer.write(List.of(expectedValue)).getJson());
            verify(additionalTimeService).updateFromJson(anyList());
        }
    }

    @Nested
    @DisplayName("returns CREATED status after added entities to database")
    class StatusCode {

        @Test
        @DisplayName("from file")
        void UpdateFromFile_WhenAddedEntitiesFromFile_ThenReturnCreatedHttpsStatus() throws Exception {
            // Given
            given(dataImportService.importFromFile(same(additionalTimeService), anyString())).willReturn(List.of());

            ReflectionTestUtils.setField(additionalTimeController, "additionalTimeConverterName", "");


            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/additional-time/file"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        }

        @Test
        @DisplayName("from json")
        void UpdateFromFile_WhenAddedEntitiesFromJson_ThenReturnCreatedHttpsStatus() throws Exception {
            // Given
            given(additionalTimeService.updateFromJson(anyList())).willReturn(List.of());

            ReflectionTestUtils.setField(additionalTimeController, "additionalTimeConverterName", "");


            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/additional-time")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("[{}]"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        }
    }
}