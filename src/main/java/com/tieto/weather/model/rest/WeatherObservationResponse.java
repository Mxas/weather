package com.tieto.weather.model.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * {@link WeatherObservation} container,
 * {@link WeatherResponseInfo} container,
 * only needed for the nice binding when rendering multiple observation response data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherObservationResponse {

    @JsonProperty("response")
    private WeatherResponseInfo responseInfo;

    @JsonProperty("current_observation")
    private WeatherObservation observation;

    public WeatherResponseInfo getResponseInfo() {
        return responseInfo;
    }

    public WeatherObservation getObservation() {
        return observation;
    }

    @Override
    public String toString() {
        return "WundergroundWeather [observation=" + observation + "]";
    }
}
