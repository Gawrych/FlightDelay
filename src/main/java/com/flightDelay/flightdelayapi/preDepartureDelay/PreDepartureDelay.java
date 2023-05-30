package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import jakarta.persistence.*;
import lombok.*;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PreDepartureDelay {

    @Id
    @Column(unique = true, nullable = false)
    private String generatedId;

    @JsonProperty("YEAR")
    private Integer year;

    @JsonProperty("MONTH_NUM")
    private Integer monthNum;

    @Column(columnDefinition = "DATE")
    @JsonProperty("FLT_DATE")
    private LocalDate flightDate;

    @Column(nullable = false)
    @JsonProperty("APT_ICAO")
    private String airportCode;

    @JsonProperty("FLT_DEP_1")
    private Integer numberOfDepartures;

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
