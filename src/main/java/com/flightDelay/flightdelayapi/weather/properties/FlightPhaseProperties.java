package com.flightDelay.flightdelayapi.weather.properties;

public interface FlightPhaseProperties {

     int getUpperThresholdOfCrosswindKts();

     int getLowerThresholdOfCrosswindKts();

     int getUpperThresholdOfTailwindKts();

     int getLowerThresholdOfTailwindKts();

     int getUpperThresholdOfVisibilityMeters();

     int getLowerThresholdOfVisibilityMeters();

     int getUpperThresholdOfCloudBaseMeters();

     int getLowerThresholdOfCloudBaseMeters();

     int getUpperThresholdOfRainMm();

     int getLowerThresholdOfRainMm();
}
