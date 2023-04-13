package com.flightDelay.flightdelayapi.region;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.flightDelay.flightdelayapi.airport.Airport;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Region {

    @Id
    @Column(nullable = false)
    @JsonProperty("code")
    private String isoCode;

    @JsonProperty("local_code")
    private String localCode;

    @JsonProperty("name")
    private String name;

    @JsonProperty("continent")
    private String continent;

    @JsonProperty("iso_country")
    private String isoCountry;

    @JsonProperty("keywords")
    private String keywords;

    @JsonBackReference
    @OneToOne(mappedBy = "region")
    private Airport airport;
}
