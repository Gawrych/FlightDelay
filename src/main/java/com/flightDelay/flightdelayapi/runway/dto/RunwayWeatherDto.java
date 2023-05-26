package com.flightDelay.flightdelayapi.runway.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class RunwayWeatherDto {

    @NotNull
    private Long id;

    private boolean lighted;

    private int leHeadingDegT;

    private int heHeadingDegT;

    private int averageElevationFt;
}
