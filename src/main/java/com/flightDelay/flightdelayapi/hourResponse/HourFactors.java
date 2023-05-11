package com.flightDelay.flightdelayapi.hourResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.DelayFactor.DelayFactor;
import com.flightDelay.flightdelayapi.weather.taf.TafReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public record HourFactors (

        TafReport dayReport,

        List<TafReport> changes
) {}
