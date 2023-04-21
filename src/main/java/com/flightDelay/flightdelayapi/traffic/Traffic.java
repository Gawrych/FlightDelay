package com.flightDelay.flightdelayapi.traffic;

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
public class Traffic {

    @Id
    @Column(unique = true)
    private String generatedId;

    @JsonProperty("APT_ICAO")
    @Column(nullable = false)
    private String airportCode;

    @JsonProperty("YEAR")
    @Column(nullable = false)
    private Integer year;

    @JsonProperty("MONTH_NUM")
    @Column(nullable = false)
    private Integer monthNum;
    @JsonProperty("FLT_DATE")
    @Column(nullable = false)
    private Long flightDate;

    @JsonProperty("FLT_DEP_1")
    private Integer flightDepartures;

    @JsonProperty("FLT_ARR_1")
    private Integer flightArrival;

    @JsonProperty("FLT_TOT_1")
    private Integer flightTotal;

    @JsonProperty("FLT_DEP_IFR_2")
    private Integer flightDeparturesInstrumentFlightRules;

    @JsonProperty("FLT_ARR_IFR_2")
    private Integer flightArrivalInstrumentFlightRules;

    @JsonProperty("FLT_TOT_IFR_2")
    private Integer flightTotalInstrumentFlightRules;

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

    public void setAirportBidirectionalRelationshipByCode(String airportCode, AirportService airportService) {
        Airport airport = airportService.findByAirportIdent(airportCode);
        if (airport != null) {
            this.setAirport(airport);
            airport.getTrafficReports().add(this);
        }
    }
}
