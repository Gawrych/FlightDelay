package com.flightDelay.flightdelayapi.statisticsFactors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ValueWithTextHolder {

    private String text;

    private double value;
}
