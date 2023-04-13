package com.flightDelay.flightdelayapi.arrivalDelay;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.airport.Airport;
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
    private Integer flightArrival;

    @JsonProperty("DLY_APT_ARR_1")
    private Integer delayedAirportArrival;

//    TODO: Change these abbreviation letters, and change delayedAirportArrival to causedBy, causedByEquipment
    @JsonProperty("DLY_APT_ARR_A_1")
    private Integer delayedAirportArrivalA;

    @JsonProperty("DLY_APT_ARR_C_1")
    private Integer delayedAirportArrivalC;

    @JsonProperty("DLY_APT_ARR_D_1")
    private Integer delayedAirportArrivalD;

    @JsonProperty("DLY_APT_ARR_E_1")
    private Integer delayedAirportArrivalE;

    @JsonProperty("DLY_APT_ARR_G_1")
    private Integer delayedAirportArrivalG;

    @JsonProperty("DLY_APT_ARR_I_1")
    private Integer delayedAirportArrivalI;

    @JsonProperty("DLY_APT_ARR_M_1")
    private Integer delayedAirportArrivalM;

    @JsonProperty("DLY_APT_ARR_N_1")
    private Integer delayedAirportArrivalN;

    @JsonProperty("DLY_APT_ARR_O_1")
    private Integer delayedAirportArrivalO;

    @JsonProperty("DLY_APT_ARR_P_1")
    private Integer delayedAirportArrivalP;

    @JsonProperty("DLY_APT_ARR_R_1")
    private Integer delayedAirportArrivalR;

    @JsonProperty("DLY_APT_ARR_S_1")
    private Integer delayedAirportArrivalS;

    @JsonProperty("DLY_APT_ARR_T_1")
    private Integer delayedAirportArrivalT;

    @JsonProperty("DLY_APT_ARR_V_1")
    private Integer delayedAirportArrivalV;

    @JsonProperty("DLY_APT_ARR_W_1")
    private Integer delayedAirportArrivalW;

    @JsonProperty("DLY_APT_ARR_NA_1")
    private Integer delayedAirportArrivalNA;

    @JsonProperty("FLT_ARR_1_DLY")
    private Integer flightArrivalDelay;

    @JsonProperty("FLT_ARR_1_DLY_15")
    private Integer flightArrivalDelay15;

    @JsonProperty("FLT_DATE")
    public void setFlightDate(long flightDate) {
        this.flightDate = java.time.Duration.ofMillis(flightDate).toDays();
    }

    @PrePersist
    public void autoGenerateId() {
        this.generatedId = generateId();
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airportIdent")
    private Airport airport;

    public String generateId() {
        return this.flightDate + "-" + this.airportCode;
    }

    public void setAirportBidirectionalRelationshipByCode(String airportCode, AirportServiceImpl airportService) {
        Airport airport = airportService.findByAirportIdent(airportCode);
        if (airport != null) {
            this.setAirport(airport);
            airport.getArrivalDelays().add(this);
        }
    }
}
