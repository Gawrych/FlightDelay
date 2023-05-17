package com.flightDelay.flightdelayapi.preDepartureDelay;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.airport.Airport;
import com.flightDelay.flightdelayapi.airport.AirportService;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    @JsonProperty("FLT_DATE")
    private Long flightDate;

    @Column(nullable = false)
    @JsonProperty("APT_ICAO")
    private String airportCode;

    @JsonProperty("FLT_DEP_1")
    private Integer flightDeparture;

    @JsonProperty("DLY_ATC_PRE_2")
    private Integer delayAtc;

    @JsonProperty("FLT_DATE")
    public void setFlightDate(long flightDate) {
        this.flightDate = java.time.Duration.ofMillis(flightDate).toDays();
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
