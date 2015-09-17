package com.tieto.weather.model.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * DTO class for weather observation's.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDispalyLocation {

    @JsonProperty("city")
    private String city;

    @JsonProperty("state_name")
    private String country;

    @JsonProperty(value = "country_iso3166")
    private String countryiso3166;

    @Override
    public String toString() {
        return String.format("[city=%s], [country=%s], [countryiso3166=%s]", city, country, countryiso3166);
    }

    public String getCountry() {
        return country;
    }

    public String getCountryiso3166() {
        return countryiso3166;
    }

    public String getCity() {
        return city;
    }
}
