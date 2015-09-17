package com.tieto.weather.model.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * DTO class for weather ambiguous result. 
 *
 * @author MindaugasK
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherAmbiguousResult {

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "country")
    private String country;

    @JsonProperty(value = "zmw")
    private String zmw;

    @JsonProperty(value = "country_name")
    private String countryName;

    public String getCountryName() {
        return countryName;
    }

    public String getZmw() {
        return zmw;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return String.format("WeatherAmbiguousResult [name=%s], [country=%s], [countryName=%s], [zmw=%s]", name, country, countryName, zmw);
    }
}
