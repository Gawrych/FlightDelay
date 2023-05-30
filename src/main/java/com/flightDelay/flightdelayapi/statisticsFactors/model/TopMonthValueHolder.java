package com.flightDelay.flightdelayapi.statisticsFactors.model;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TopMonthValueHolder {

    private String monthName;

    private int monthNum;

    private double value;
}

