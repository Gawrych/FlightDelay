package com.flightDelay.flightdelayapi.weatherFactors;

import com.flightDelay.flightdelayapi.weatherFactors.dto.AirportWeatherDto;
import com.flightDelay.flightdelayapi.runway.RunwayDto;
import com.flightDelay.flightdelayapi.weatherFactors.enums.IlsCategory;
import com.flightDelay.flightdelayapi.weatherFactors.calculators.InstrumentLandingSystemCalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class InstrumentLandingSystemCalculatorImplTest {

    private RunwayWeatherCalculatorTestImpl weatherCalculatorTest;

    private AirportWeatherDto airportWeatherDto;

    @BeforeEach
    void setUp() {
        RunwayDto runwayDto = new RunwayDto();
        runwayDto.setId(1L);

        weatherCalculatorTest = new RunwayWeatherCalculatorTestImpl();
        airportWeatherDto = new AirportWeatherDto(null, null, 0, null, null);
    }

    @Test
    void whenConditionsAreEnoughForIls0_thenReturnIlsCategory0() {
        weatherCalculatorTest.setCategoryValues(IlsCategory.NONPRECISION);
        InstrumentLandingSystemCalculatorImpl instrumentLandingSystemCalculator = new InstrumentLandingSystemCalculatorImpl(weatherCalculatorTest);
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);
        assertThat(ilsCategory).isEqualTo(IlsCategory.NONPRECISION);
    }

    @Test
    void whenConditionsAreEnoughForIls1_thenReturnIlsCategory1() {
        weatherCalculatorTest.setCategoryValues(IlsCategory.CATEGORY_1);
        InstrumentLandingSystemCalculatorImpl instrumentLandingSystemCalculator = new InstrumentLandingSystemCalculatorImpl(weatherCalculatorTest);
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);
        assertThat(ilsCategory).isEqualTo(IlsCategory.CATEGORY_1);
    }

    @Test
    void whenConditionsAreEnoughForIls2_thenReturnIlsCategory2() {
        weatherCalculatorTest.setCategoryValues(IlsCategory.CATEGORY_2);
        InstrumentLandingSystemCalculatorImpl instrumentLandingSystemCalculator = new InstrumentLandingSystemCalculatorImpl(weatherCalculatorTest);
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);
        assertThat(ilsCategory).isEqualTo(IlsCategory.CATEGORY_2);
    }

    @Test
    void whenConditionsAreEnoughForIls3A_thenReturnIlsCategory3A() {
        weatherCalculatorTest.setCategoryValues(IlsCategory.CATEGORY_3A);
        InstrumentLandingSystemCalculatorImpl instrumentLandingSystemCalculator = new InstrumentLandingSystemCalculatorImpl(weatherCalculatorTest);
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);
        assertThat(ilsCategory).isEqualTo(IlsCategory.CATEGORY_3A);
    }

    @Test
    void whenConditionsAreEnoughForIls3B_thenReturnIlsCategory3B() {
        weatherCalculatorTest.setCategoryValues(IlsCategory.CATEGORY_3B);
        InstrumentLandingSystemCalculatorImpl instrumentLandingSystemCalculator = new InstrumentLandingSystemCalculatorImpl(weatherCalculatorTest);
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);
        assertThat(ilsCategory).isEqualTo(IlsCategory.CATEGORY_3B);

    }

    @Test
    void whenConditionsAreEnoughForIls3C_thenReturnIlsCategory3C() {
        weatherCalculatorTest.setCategoryValues(IlsCategory.CATEGORY_3C);
        InstrumentLandingSystemCalculatorImpl instrumentLandingSystemCalculator = new InstrumentLandingSystemCalculatorImpl(weatherCalculatorTest);
        IlsCategory ilsCategory = instrumentLandingSystemCalculator.getMinRequiredCategory(airportWeatherDto);
        assertThat(ilsCategory).isEqualTo(IlsCategory.CATEGORY_3C);
    }
}