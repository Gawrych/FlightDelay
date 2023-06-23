package com.flightDelay.flightdelayapi.preDepartureDelay;

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
public class PreDepartureDelay {

    @Id
    @Column(name = "`generatedId`", unique = true, nullable = false)
    private String generatedId;

    @Column(name = "`year`", nullable = false)
    @JsonProperty("YEAR")
    private Integer year;

    @Column(name = "`monthNum`", nullable = false)
    @JsonProperty("MONTH_NUM")
    private Integer monthNum;

    @Column(name = "`flightDate`", columnDefinition = "DATE")
    @JsonProperty("FLT_DATE")
    private LocalDate flightDate;

    @Column(name = "`airportCode`", nullable = false)
    @JsonProperty("APT_ICAO")
    private String airportCode;

    @Column(name = "`numberOfDepartures`")
    @JsonProperty("FLT_DEP_1")
    private Integer numberOfDepartures;

    @Column(name = "`delayInMinutes`")
    @JsonProperty("DLY_ATC_PRE_2")
    private Integer delayInMinutes;

    @JsonProperty("FLT_DATE")
    public void setFlightDate(long flightDateMillis) {
        Instant instant = Instant.ofEpochMilli(flightDateMillis);
        this.flightDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @PrePersist
    public void autoGenerateId() {
        this.generatedId = generateId();
    }

    public String generateId() {
        return this.flightDate + "-" + this.airportCode;
    }

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airportIdent")
    private Airport airport;
}
