package com.tieto.weather.model.rest;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * DTO class for weather observation's.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherObservation {

    @JsonProperty("local_tz_long")
    private String location;

    @JsonProperty(value = "temp_c")
    private double temperature;

    @JsonProperty(value = "relative_humidity")
    private String humidity;

    private String weather;

    @JsonProperty(value = "wind_dir")
    private String windDirection;

    @JsonProperty(value = "wind_string")
    private String wind;

    @JsonProperty(value = "display_location")
    private WeatherDispalyLocation displayLocation;

    @Override
    public String toString() {
        return String.format("[location=%s], [temperature=%s], [displayLocation=%s]", location, temperature, displayLocation);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public WeatherDispalyLocation getDisplayLocation() {
        return displayLocation;
    }
}
