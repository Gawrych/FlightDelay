package com.flightDelay.flightdelayapi.additionalTime;

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
public class AdditionalTime {

    @Id
    @Column(name = "`generatedId`", unique = true, nullable = false)
    private String generatedId;

    @Column(name = "`year`", nullable = false)
    @JsonProperty("YEAR")
    private Integer year;

    @Column(name = "`monthNum`", nullable = false)
    @JsonProperty("MONTH_NUM")
    private Integer monthNum;

    @Column(name = "`date`", columnDefinition = "DATE")
    @JsonProperty("DATE")
    private LocalDate date;

    @Column(name = "`stage`", nullable = false)
    @JsonProperty("STAGE")
    private String stage;

    @Column(name = "`airportCode`", nullable = false)
    @JsonProperty("APT_ICAO")
    private String airportCode;

    @Column(name = "`totalFlight`")
    @JsonProperty("TOTAL_REF_NB_FL")
    private Integer totalFlight;

    @Column(name = "`totalReferenceTimeInMinutes`")
    @JsonProperty("TOTAL_REF_TIME_MIN")
    private Integer totalReferenceTimeInMinutes;

    @Column(name = "`totalAdditionalTimeInMinutes`")
    @JsonProperty("TOTAL_ADD_TIME_MIN")
    private Integer totalAdditionalTimeInMinutes;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airportIdent")
    private Airport airport;

    @JsonProperty("DATE")
    public void setDate(long flightDateMillis) {
        Instant instant = Instant.ofEpochMilli(flightDateMillis);
        this.date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @PrePersist
    public void autoGenerateId() {
        this.generatedId = generateId();
    }

    public String generateId() {
        return this.date + "-" + this.airportCode + "-" + this.stage;
    }
}
