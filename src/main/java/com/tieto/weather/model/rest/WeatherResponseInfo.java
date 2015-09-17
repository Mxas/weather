package com.tieto.weather.model.rest;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * DTO class for <b>responce</b> {@link #ambiguousResult} result part binding 
 * and {@link #error} part
 *
 * @author MindaugasK
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponseInfo {

    @JsonProperty(value = "error")
    private WeatherErrorResult error;

    @JsonProperty(value = "results")
    private List<WeatherAmbiguousResult> ambiguousResult;

    public List<WeatherAmbiguousResult> getAmbiguousResult() {
        return ambiguousResult;
    }

    public WeatherErrorResult getError() {
        return error;
    }

    @Override
    public String toString() {
        return String.format("WeatherAmbiguousResult [error=%s], [ambiguousResult=%s]", error, ambiguousResult);
    }
}
