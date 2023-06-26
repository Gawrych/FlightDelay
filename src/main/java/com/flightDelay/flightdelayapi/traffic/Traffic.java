package com.flightDelay.flightdelayapi.traffic;

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
public class Traffic {

    @Id
    @Column(name = "`generatedId`", unique = true, nullable = false)
    private String generatedId;

    @JsonProperty("APT_ICAO")
    @Column(name = "`airportCode`", nullable = false)
    private String airportCode;

    @JsonProperty("YEAR")
    @Column(name = "`year`", nullable = false)
    private Integer year;

    @JsonProperty("MONTH_NUM")
    @Column(name = "`monthNum`", nullable = false)
    private Integer monthNum;

    @Column(name = "`date`", columnDefinition = "DATE")
    @JsonProperty("DATE")
    private LocalDate date;

    @JsonProperty("FLT_DEP_1")
    private Integer departures;

    @JsonProperty("FLT_ARR_1")
    private Integer arrivals;

    @JsonProperty("FLT_TOT_1")
    private Integer total;

    @JsonProperty("FLT_DEP_IFR_2")
    private Integer flightDeparturesInstrumentFlightRules;

    @JsonProperty("FLT_ARR_IFR_2")
    private Integer flightArrivalInstrumentFlightRules;

    @JsonProperty("FLT_TOT_IFR_2")
    private Integer flightTotalInstrumentFlightRules;

    @JsonProperty("DATE")
    public void setDate(long flightDateMillis) {
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
