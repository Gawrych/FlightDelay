package com.flightDelay.flightdelayapi.arrivalDelay;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import com.flightDelay.flightdelayapi.airport.AirportServiceImpl;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArrivalDelay {

    @Id
    @Column(unique = true, nullable = false)
    private String generatedId;

    @JsonProperty("YEAR")
    @Column(nullable = false)
    private Integer year;

    @JsonProperty("MONTH_NUM")
    @Column(nullable = false)
    private Integer month;

    @JsonProperty("FLT_DATE")
    @Column(nullable = false)
    private Long flightDate;

    @JsonProperty("APT_ICAO")
    @Column(nullable = false)
    private String airportCode;

    @JsonProperty("FLT_ARR_1")
    private Integer numberOfArrivals;

    @JsonProperty("DLY_APT_ARR_1")
    private Integer minutesOfAirportDelay;

    @JsonProperty("DLY_APT_ARR_A_1")
    private Integer delayInMinutesCausedByAccident;

    @JsonProperty("DLY_APT_ARR_C_1")
    private Integer delayInMinutesCausedByCapacity;

    @JsonProperty("DLY_APT_ARR_D_1")
    private Integer delayInMinutesCausedByDeicing;

    @JsonProperty("DLY_APT_ARR_E_1")
    private Integer delayInMinutesCausedByEquipment;

    @JsonProperty("DLY_APT_ARR_G_1")
    private Integer delayInMinutesCausedByAerodromeCapacity;

    @JsonProperty("DLY_APT_ARR_I_1")
    private Integer delayInMinutesCausedByIndustrialActionATC;

    @JsonProperty("DLY_APT_ARR_M_1")
    private Integer delayInMinutesCausedByAirspaceManagement;

    @JsonProperty("DLY_APT_ARR_N_1")
    private Integer delayInMinutesCausedByIndustrialAction;

    @JsonProperty("DLY_APT_ARR_O_1")
    private Integer delayInMinutesCausedByOther;

    @JsonProperty("DLY_APT_ARR_P_1")
    private Integer delayInMinutesCausedBySpecialEvent;

    @JsonProperty("DLY_APT_ARR_R_1")
    private Integer delayInMinutesCausedByRouteing;

    @JsonProperty("DLY_APT_ARR_S_1")
    private Integer delayInMinutesCausedByStaffing;

    @JsonProperty("DLY_APT_ARR_T_1")
    private Integer delayInMinutesCausedByEquipmentATC;

    @JsonProperty("DLY_APT_ARR_V_1")
    private Integer delayInMinutesCausedByEnvironmentalIssues;

    @JsonProperty("DLY_APT_ARR_W_1")
    private Integer delayInMinutesCausedByWeather;

    @JsonProperty("DLY_APT_ARR_NA_1")
    private Integer delayInMinutesCausedByNotSpecified;

    @JsonProperty("FLT_ARR_1_DLY")
    private Integer numberOfDelayedArrivals;

    @JsonProperty("FLT_ARR_1_DLY_15")
    private Integer numberOfDelayedArrivalsAbove15Minutes;

    @JsonProperty("FLT_DATE")
    public void setFlightDate(long flightDate) {
        this.flightDate = java.time.Duration.ofMillis(flightDate).toDays();
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
        return this.flightDate + "-" + this.airportCode;
    }
}
