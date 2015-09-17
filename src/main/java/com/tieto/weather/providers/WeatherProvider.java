package com.tieto.weather.providers;

import com.tieto.weather.model.rest.WeatherObservation;

/**
 * Weather provider.
 */
public interface WeatherProvider {

    /**
     * Returns {@link WeatherObservation} from weather provider.
     *
     * @param city - search criteria 
     * @return {@link WeatherObservation} 
     */
    public WeatherObservation getWeather(final String city);
}
