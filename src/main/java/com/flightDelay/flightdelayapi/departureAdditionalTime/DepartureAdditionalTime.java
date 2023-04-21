package com.flightDelay.flightdelayapi.departureAdditionalTime;

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
public class DepartureAdditionalTime {
    @Id
    @Column(unique = true, nullable = false)
    private String generatedId;

    @Column(nullable = false)
    @JsonProperty("YEAR")
    private Integer year;

    @Column(nullable = false)
    @JsonProperty("MONTH_NUM")
    private Integer monthNum;

    @Column(nullable = false)
    @JsonProperty("FLT_DATE")
    private Long flightDate;

    @Column(nullable = false)
    @JsonProperty("STAGE")
    private String stage;

    @Column(nullable = false)
    @JsonProperty("APT_ICAO")
    private String airportCode;

    @JsonProperty("TOTAL_REF_NB_FL")
    private Integer totalDepartureFlight;

    @JsonProperty("TOTAL_REF_TIME_MIN")
    private Double totalReferenceTimeMin;

    @JsonProperty("TOTAL_ADD_TIME_MIN")
    private Double totalAdditionalTimeMin;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airportIdent")
    private Airport airport;

    @PrePersist
    public void autoGenerateId() {
        this.generatedId = generateId();
    }

    public String generateId() {
        return this.flightDate + "-" + this.airportCode + "-" + this.stage;
    }

    public void setAirportBidirectionalRelationshipByCode(String airportCode, AirportService airportService) {
        Airport airport = airportService.findByAirportIdent(airportCode);
        if (airport != null) {
            this.setAirport(airport);
            airport.getDepartureAdditionalTimes().add(this);
        }
    }
}
