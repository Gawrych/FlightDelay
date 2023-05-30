package com.flightDelay.flightdelayapi.additionalTime;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalTimeDto {

    private LocalDate date;

    private double totalFlight;

    private double totalAdditionalTimeInMinutes;
}
