package com.flightDelay.flightdelayapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RunwayDto {

    @JsonProperty("lighted")
    private Boolean lighted;

    @Column(nullable = false)
    @JsonProperty("le_elevation_ft")
    private Integer leElevationFt;

    @Column(nullable = false)
    @JsonProperty("le_heading_degT")
    private Integer leHeadingDegT;

    @Column(nullable = false)
    @JsonProperty("he_elevation_ft")
    private Integer heElevationFt;

    @Column(nullable = false)
    @JsonProperty("he_heading_degT")
    private Integer heHeadingDegT;
}
