package com.flightDelay.flightdelayapi.additionalTime;

import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalTimeDto implements DelayEntityDto {

    private LocalDate date;

    private double totalFlight;

    private double totalAdditionalTimeInMinutes;
}
