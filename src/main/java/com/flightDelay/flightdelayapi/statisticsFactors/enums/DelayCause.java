package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelay;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum DelayCause {

    ACCIDENT(ArrivalDelay::getDelayInMinutesCausedByAccident, "DISRUPTIONS"),

    CAPACITY(ArrivalDelay::getDelayInMinutesCausedByCapacity, "CAPACITY"),

    DEICING(ArrivalDelay::getDelayInMinutesCausedByDeicing, "WEATHER"),

    EQUIPMENT(ArrivalDelay::getDelayInMinutesCausedByEquipment, "DISRUPTIONS"),

    AERODROME_CAPACITY(ArrivalDelay::getDelayInMinutesCausedByAerodromeCapacity, "CAPACITY"),

    INDUSTRIAL_ACTION_ATC(ArrivalDelay::getDelayInMinutesCausedByIndustrialActionATC, "DISRUPTIONS"),

    AIRSPACE_MANAGEMENT(ArrivalDelay::getDelayInMinutesCausedByAirspaceManagement, "CAPACITY"),

    INDUSTRIAL_ACTION(ArrivalDelay::getDelayInMinutesCausedByIndustrialAction, "DISRUPTIONS"),

    OTHER(ArrivalDelay::getDelayInMinutesCausedByOther, "DISRUPTIONS"),

    SPECIAL_EVENT(ArrivalDelay::getDelayInMinutesCausedBySpecialEvent, "EVENTS"),

    ROUTEING(ArrivalDelay::getDelayInMinutesCausedByRouteing, "CAPACITY"),

    STAFFING(ArrivalDelay::getDelayInMinutesCausedByStaffing, "STAFFING"),

    EQUIPMENT_ATC(ArrivalDelay::getDelayInMinutesCausedByEquipmentATC, "DISRUPTIONS"),

    ENVIRONMENTAL_ISSUES(ArrivalDelay::getDelayInMinutesCausedByEnvironmentalIssues, "CAPACITY"),

    WEATHER(ArrivalDelay::getDelayInMinutesCausedByWeather, "WEATHER"),

    NOT_SPECIFIED(ArrivalDelay::getDelayInMinutesCausedByNotSpecified, "DISRUPTIONS");

    private final Function<ArrivalDelay, Integer> causeReference;

    private final String reasonGroup;
}
