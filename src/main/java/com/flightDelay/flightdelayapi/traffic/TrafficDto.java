package com.flightDelay.flightdelayapi.traffic;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficDto {

    private LocalDate date;

    private int departures;

    private int arrivals;

    private int total;
}
