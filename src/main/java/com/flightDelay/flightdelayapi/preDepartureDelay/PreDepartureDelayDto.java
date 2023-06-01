package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreDepartureDelayDto implements DelayEntityDto {

    private LocalDate date;

    private double numberOfDepartures;

    private double delayInMinutes;
}
