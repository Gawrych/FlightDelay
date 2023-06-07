package com.flightDelay.flightdelayapi.arrivalDelay;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.statisticsFactors.enums.DelayCause;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalDelayDto implements DelayEntityDto {

    private LocalDate date;

    private int numberOfArrivals;

    private int minutesOfAirportDelay;

    private int numberOfDelayedArrivals;

    private Map<DelayCause, Integer> delays;
}
