package com.tieto.weather.model.rest;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * DTO class for weather error result. 
 *
 * @author MindaugasK
 *
 */
public class WeatherErrorResult {

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "description")
    private String description;

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("WeatherAmbiguousResult [type=%s], [description=%s]", type, description);
    }

}
