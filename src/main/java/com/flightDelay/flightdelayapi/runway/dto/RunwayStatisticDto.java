package com.flightDelay.flightdelayapi.runway.dto;

import jakarta.validation.constraints.NotNull;

public class RunwayStatisticDto {

    @NotNull
    private Long id;

    private Boolean lighted;

    private Integer lengthFt;

    private String surface;

    private String leIdent;

    private String heIdent;
}
