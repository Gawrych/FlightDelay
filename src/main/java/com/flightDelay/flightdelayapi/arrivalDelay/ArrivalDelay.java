package com.flightDelay.flightdelayapi.arrivalDelay;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.airport.Airport;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrivalDelay {

    @Id
    @Column(name = "`generatedId`", unique = true, nullable = false)
    private String generatedId;

    @JsonProperty("YEAR")
    @Column(name = "`year`", nullable = false)
    private Integer year;

    @JsonProperty("MONTH_NUM")
    @Column(name = "`month`", nullable = false)
    private Integer month;

    @JsonProperty("FLT_DATE")
    @Column(name = "`date`", columnDefinition = "DATE")
    private LocalDate date;

    @JsonProperty("APT_ICAO")
    @Column(name = "`airportCode`", nullable = false)
    private String airportCode;

    @JsonProperty("FLT_ARR_1")
    private int numberOfArrivals;

    @JsonProperty("DLY_APT_ARR_1")
    private int minutesOfAirportDelay;

    @JsonProperty("DLY_APT_ARR_A_1")
    private int delayInMinutesCausedByAccident;

    @JsonProperty("DLY_APT_ARR_C_1")
    private int delayInMinutesCausedByCapacity;

    @JsonProperty("DLY_APT_ARR_D_1")
    private int delayInMinutesCausedByDeicing;

    @JsonProperty("DLY_APT_ARR_E_1")
    private int delayInMinutesCausedByEquipment;

    @JsonProperty("DLY_APT_ARR_G_1")
    private int delayInMinutesCausedByAerodromeCapacity;

    @JsonProperty("DLY_APT_ARR_I_1")
    private int delayInMinutesCausedByIndustrialActionATC;

    @JsonProperty("DLY_APT_ARR_M_1")
    private int delayInMinutesCausedByAirspaceManagement;

    @JsonProperty("DLY_APT_ARR_N_1")
    private int delayInMinutesCausedByIndustrialAction;

    @JsonProperty("DLY_APT_ARR_O_1")
    private int delayInMinutesCausedByOther;

    @JsonProperty("DLY_APT_ARR_P_1")
    private int delayInMinutesCausedBySpecialEvent;

    @JsonProperty("DLY_APT_ARR_R_1")
    private int delayInMinutesCausedByRouteing;

    @JsonProperty("DLY_APT_ARR_S_1")
    private int delayInMinutesCausedByStaffing;

    @JsonProperty("DLY_APT_ARR_T_1")
    private int delayInMinutesCausedByEquipmentATC;

    @JsonProperty("DLY_APT_ARR_V_1")
    private int delayInMinutesCausedByEnvironmentalIssues;

    @JsonProperty("DLY_APT_ARR_W_1")
    private int delayInMinutesCausedByWeather;

    @JsonProperty("DLY_APT_ARR_NA_1")
    private int delayInMinutesCausedByNotSpecified;

    @JsonProperty("FLT_ARR_1_DLY")
    private int numberOfDelayedArrivals;

    @JsonProperty("FLT_ARR_1_DLY_15")
    private int numberOfDelayedArrivalsAbove15Minutes;

    @JsonProperty("FLT_DATE")
    public void setFlightDate(long flightDateMillis) {
        Instant instant = Instant.ofEpochMilli(flightDateMillis);
        this.date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @PrePersist
    public void autoGenerateId() {
        this.generatedId = generateId();
    }

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airportIdent")
    private Airport airport;

    public String generateId() {
        return this.date + "-" + this.airportCode;
    }
}
