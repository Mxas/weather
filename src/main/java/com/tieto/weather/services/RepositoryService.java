package com.tieto.weather.services;

import java.util.Collection;

import com.tieto.weather.model.rest.WeatherObservation;

/**
 * Repository service.
 *
 * Returns cached {@link WeatherObservation}s retrieved from {@link WeatherProviderClient}.
 */
public interface RepositoryService {

    /**
     * Returns cached {@link WeatherObservation} by city.
     *
     * @param city
     * @return {@link WeatherObservation}
     */
    WeatherObservation getWeather(final String city);

    /**
     * Returns cached {@link WeatherObservation}s by city.
     *
     * @return Collection<{@link WeatherObservation}>
     */
    Collection<WeatherObservation> getWeather();
}
