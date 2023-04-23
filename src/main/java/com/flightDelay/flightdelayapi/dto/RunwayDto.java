package com.flightDelay.flightdelayapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class RunwayDto {

    @NotNull
    private Long id;

    private boolean lighted;

    private int leElevationFt;

    private int leHeadingDegT;

    private int heElevationFt;

    private int heHeadingDegT;

    private int averageElevationFt;
}
