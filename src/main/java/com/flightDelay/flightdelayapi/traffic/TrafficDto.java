package com.flightDelay.flightdelayapi.traffic;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrafficDto implements DelayEntityDto {

    private LocalDate date;

    private int departures;

    private int arrivals;

    private int total;
}
