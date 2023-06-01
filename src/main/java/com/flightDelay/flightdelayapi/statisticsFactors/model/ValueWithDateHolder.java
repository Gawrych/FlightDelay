package com.flightDelay.flightdelayapi.statisticsFactors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ValueWithDateHolder {

    private LocalDate date;

    private double value;
}

