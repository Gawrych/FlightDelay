package com.flightDelay.flightdelayapi.statisticsFactors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flightDelay.flightdelayapi.statisticsFactors.model.PrecisionReport;
import com.flightDelay.flightdelayapi.statisticsFactors.model.StatisticReport;
import com.flightDelay.flightdelayapi.statisticsFactors.service.StatisticFactorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@DisplayName("The statistic factor controller")
class StatisticFactorControllerTest {

    private MockMvc mvc;

    @Mock
    public StatisticFactorService statisticFactorService;

    private JacksonTester<Map<String, StatisticReport>> jsonDeserializer;

    @InjectMocks
    private StatisticFactorController statisticFactorController;

    @BeforeEach
    void setUp() {
        JacksonTester.initFields(this, new ObjectMapper());
        this.mvc = MockMvcBuilders.standaloneSetup(statisticFactorController).build();
    }

    @Test
    @DisplayName("return names and statistics reports in map from statistic factor service")
    void CalculateTopMonthDelay_WhenPassValidListAsAParameter_ThenReturnCorrectResult() throws Exception {
        // Given
        Map<String, PrecisionReport> expectedMap = new HashMap<>();

        given(statisticFactorService.getFactorsByPhase(anyString())).willReturn(expectedMap);

        // When
        MockHttpServletResponse response = mvc
                .perform(post("/api/v1/statistics/AAAA"))
                .andReturn()
                .getResponse();

        // Then
        then(response.getContentAsString())
                .isEqualTo(jsonDeserializer.write(new HashMap<>()).getJson());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AAAA", "BBBB", "CCCC"})
    @DisplayName("pass airport code to statistics factor service")
    void CalculateTopMonthDelay_WhenGetValidAirportCode_ThenReturnCorrectResult(String airportCode) throws Exception {
        // When
        mvc.perform(post("/api/v1/statistics/" + airportCode))
                .andReturn()
                .getResponse();

        // Then
        verify(statisticFactorService).getFactorsByPhase(airportCode);
    }

    @Test
    @DisplayName("return Ok status when statistic factor service return a map")
    void CalculateTopMonthDelay_WhenGet_ThenReturnCorrectResult() throws Exception {
        // Given
        given(statisticFactorService.getFactorsByPhase(anyString())).willReturn(Map.of());

        // When
        MockHttpServletResponse response = mvc
                .perform(post("/api/v1/statistics/AAAA"))
                .andReturn()
                .getResponse();

        // Then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}