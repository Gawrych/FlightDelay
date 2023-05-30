package com.flightDelay.flightdelayapi.additionalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.airport.Airport;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdditionalTime {

    @Id
    @NotNull
    @Column(unique = true, nullable = false)
    private String generatedId;

    @Column(nullable = false)
    @JsonProperty("YEAR")
    private Integer year;

    @Column(nullable = false)
    @JsonProperty("MONTH_NUM")
    private Integer monthNum;

    @Column(columnDefinition = "DATE")
    @JsonProperty("FLT_DATE")
    private LocalDate flightDate;

    @Column(nullable = false)
    @JsonProperty("STAGE")
    private String stage;

    @Column(nullable = false)
    @JsonProperty("APT_ICAO")
    private String airportCode;

    @JsonProperty("TOTAL_REF_NB_FL")
    private Integer totalFlight;

    @JsonProperty("TOTAL_REF_TIME_MIN")
    private Integer totalReferenceTimeInMinutes;

    @JsonProperty("TOTAL_ADD_TIME_MIN")
    private Integer totalAdditionalTimeInMinutes;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airportIdent")
    private Airport airport;

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
        return this.flightDate + "-" + this.airportCode + "-" + this.stage;
    }
}