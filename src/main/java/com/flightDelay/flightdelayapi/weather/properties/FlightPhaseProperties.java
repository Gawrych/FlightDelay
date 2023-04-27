package com.flightDelay.flightdelayapi.weather.properties;

public interface FlightPhaseProperties {

     int getUpperThresholdOfCrosswindKt();

     int getLowerThresholdOfCrosswindKt();

     int getUpperThresholdOfTailwindKt();

     int getLowerThresholdOfTailwindKt();

     int getUpperThresholdOfVisibilityMeters();

     int getLowerThresholdOfVisibilityMeters();

     int getUpperThresholdOfCloudBaseMeters();

     int getLowerThresholdOfCloudBaseMeters();

     int getUpperThresholdOfRainMm();

     int getLowerThresholdOfRainMm();
}
