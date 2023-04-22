package com.flightDelay.flightdelayapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class RunwayDto {

    @NotNull
    private Boolean lighted;

    @NotNull
    private Integer leElevationFt;

    @NotNull
    private Integer leHeadingDegT;

    @NotNull
    private Integer heElevationFt;

    @NotNull
    private Integer heHeadingDegT;
}
