package com.flightDelay.flightdelayapi.additionalTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.flightDelay.flightdelayapi.shared.DelayEntityDto;
import com.flightDelay.flightdelayapi.shared.validator.EnumValidator;
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

    @JsonDeserialize(using = AdditionalTimeStageEnumDeserializer.class)
    @EnumValidator(enumClass = AdditionalTimeStage.class, message = "{error.message.additionalTimeStageEnumDeserializer}")
    private AdditionalTimeStage stage;
}
