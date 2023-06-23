package com.flightDelay.flightdelayapi.runway;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(MockitoExtension.class)
@DisplayName("The runway controller")
class RunwayControllerTest {

    private MockMvc mvc;

    @Mock
    private RunwayService runwayService;

    @InjectMocks
    private RunwayController runwayController;

    private JacksonTester<List<Runway>> jsonSerializer;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        this.mvc = MockMvcBuilders.standaloneSetup(runwayController).build();
    }

    @Nested
    @DisplayName("returns all entities added to the database")
    class ReturnsAllAddedEntities {

        @Test
        @DisplayName("from json")
        void Update_WhenAddEntitiesFromJsonToDatabase_ThenReturnAddedEntities() throws Exception {
            // Given
            Runway expectedValue = new Runway();
            given(runwayService.updateFromJson(anyString())).willReturn(List.of(expectedValue));

            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/runways")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("[{}]"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getContentAsString()).isEqualTo(jsonSerializer.write(List.of(expectedValue)).getJson());
            verify(runwayService).updateFromJson(anyString());
        }
    }

    @Nested
    @DisplayName("returns CREATED status after added entities to database")
    class StatusCode {

        @Test
        @DisplayName("from json")
        void Update_WhenAddedEntitiesFromJson_ThenReturnCreatedHttpsStatus() throws Exception {
            // Given
            given(runwayService.updateFromJson(anyString())).willReturn(List.of());

            // When
            MockHttpServletResponse response = mvc.perform(put("/api/v1/runways")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("[{}]"))
                    .andReturn()
                    .getResponse();

            // Then
            then(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        }
    }
}