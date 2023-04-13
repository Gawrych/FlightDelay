package com.flightDelay.flightdelayapi.airport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.arrivalDelay.ArrivalDelay;
import com.flightDelay.flightdelayapi.departureAdditionalTime.DepartureAdditionalTime;
import com.flightDelay.flightdelayapi.preDepartureDelay.PreDepartureDelay;
import com.flightDelay.flightdelayapi.region.Region;
import com.flightDelay.flightdelayapi.runway.Runway;
import com.flightDelay.flightdelayapi.traffic.Traffic;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airport {

    @Id
    @JsonProperty("ident")
    @Column(unique = true, nullable = false)
    private String airportIdent;

    @JsonProperty("name")
    @Column(nullable = false)
    private String name;

    @JsonProperty("latitude_deg")
    private Double latitudeDeg;

    @JsonProperty("longitude_deg")
    private Double longitudeDeg;

    @JsonProperty("iso_country")
    private String isoCountry;

    @JsonProperty("municipality")
    private String municipality;

    @JsonProperty("type")
    private String type;

    @JsonProperty("elevation_ft")
    private Integer elevationFt;

    @JsonProperty("iso_region")
    private String isoRegion;

    @JsonProperty("home_link")
    private String homeLink;

    @JsonProperty("keywords")
    private String keywords;

    @JsonManagedReference
    @OneToMany(mappedBy="airport")
    private Set<Runway> runways;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "regionIsoCode", referencedColumnName = "isoCode")
    private Region region;

    @JsonManagedReference
    @OneToMany(mappedBy="airport")
    private List<Traffic> trafficReports;

    @JsonManagedReference
    @OneToMany(mappedBy="airport")
    private List<ArrivalDelay> arrivalDelays;

    @JsonManagedReference
    @OneToMany(mappedBy="airport")
    private List<PreDepartureDelay> preDepartureDelays;

    @JsonManagedReference
    @OneToMany(mappedBy="airport")
    private List<DepartureAdditionalTime> departureAdditionalTimes;
}
