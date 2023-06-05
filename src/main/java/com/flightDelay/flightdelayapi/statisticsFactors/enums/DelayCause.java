package com.flightDelay.flightdelayapi.statisticsFactors.enums;

import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelay;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum DelayCause {

    ACCIDENT(ArrivalDelay::getDelayInMinutesCausedByAccident),

    CAPACITY(ArrivalDelay::getDelayInMinutesCausedByCapacity),

    DEICING(ArrivalDelay::getDelayInMinutesCausedByDeicing),

    EQUIPMENT(ArrivalDelay::getDelayInMinutesCausedByEquipment),

    AERODROME_CAPACITY(ArrivalDelay::getDelayInMinutesCausedByAerodromeCapacity),

    INDUSTRIAL_ACTION_ATC(ArrivalDelay::getDelayInMinutesCausedByIndustrialActionATC),

    AIRSPACE_MANAGEMENT(ArrivalDelay::getDelayInMinutesCausedByAirspaceManagement),

    INDUSTRIAL_ACTION(ArrivalDelay::getDelayInMinutesCausedByIndustrialAction),

    OTHER(ArrivalDelay::getDelayInMinutesCausedByOther),

    SPECIAL_EVENT(ArrivalDelay::getDelayInMinutesCausedBySpecialEvent),

    ROUTEING(ArrivalDelay::getDelayInMinutesCausedByRouteing),

    STAFFING(ArrivalDelay::getDelayInMinutesCausedByStaffing),

    EQUIPMENT_ATC(ArrivalDelay::getDelayInMinutesCausedByEquipmentATC),

    ENVIRONMENTAL_ISSUES(ArrivalDelay::getDelayInMinutesCausedByEnvironmentalIssues),

    WEATHER(ArrivalDelay::getDelayInMinutesCausedByWeather),

    NOT_SPECIFIED(ArrivalDelay::getDelayInMinutesCausedByNotSpecified);

    private final Function<ArrivalDelay, Integer> causeReference;
}
