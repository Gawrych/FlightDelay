package com.flightDelay.flightdelayapi.runway;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.airport.Airport;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Runway {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @JsonProperty("airport_ident")
    private String airportCode;

    @Column(nullable = false)
    @JsonProperty("length_ft")
    private Integer lengthFt;

    @JsonProperty("width_ft")
    private Integer widthFt;

    @JsonProperty("surface")
    private String surface;

    @JsonProperty("lighted")
    private Boolean lighted;

    @Column(nullable = false)
    @JsonProperty("le_ident")
    private String leIdent;

    @JsonProperty("le_latitude_deg")
    private Double leLatitudeDeg;

    @JsonProperty("le_longitude_deg")
    private Double leLongitudeDeg;

    @JsonProperty("le_elevation_ft")
    private Integer leElevationFt;

    @Column(nullable = false)
    @JsonProperty("le_heading_degT")
    private Integer leHeadingDegT;

    @Column(nullable = false)
    @JsonProperty("he_ident")
    private String heIdent;

    @JsonProperty("he_latitude_deg")
    private Double heLatitudeDeg;

    @JsonProperty("he_longitude_deg")
    private Double heLongitudeDeg;

    @JsonProperty("he_elevation_ft")
    private Integer heElevationFt;

    @Column(nullable = false)
    @JsonProperty("he_heading_degT")
    private Integer heHeadingDegT;

    @ToString.Exclude
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "airportIdent")
    private Airport airport;
}
