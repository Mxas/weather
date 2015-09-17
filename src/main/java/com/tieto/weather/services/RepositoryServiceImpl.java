package com.tieto.weather.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.providers.WeatherProvider;

/**
 * 
 * Service responsible for weather supply, popular searches auditing and
 * deciding use regular provider or cached
 *
 * @author MindaugasK
 *
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {

    @Resource(name = "weatherCachetProviderClient")
    private WeatherProvider cachedWeatherProvider;

    @Resource(name = "weatherUndergroundClient")
    private WeatherProvider undergroundWeatherProvider;

    @Autowired
    private PopularCitiesService popularCitiesService;

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherObservation getWeather(final String city) {
        switch (popularCitiesService.analizeSearch(city)) {
        case REGULAR:
            return undergroundWeatherProvider.getWeather(city);
        case CACHED:
            return cachedWeatherProvider.getWeather(city);
        default:
            return addToCache(city);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<WeatherObservation> getWeather() {
        Collection<WeatherObservation> weathers = new ArrayList<WeatherObservation>();
        for (String city : popularCitiesService.getPopularCities()) {
            weathers.add(cachedWeatherProvider.getWeather(city));
        }
        return weathers;
    }

    private WeatherObservation addToCache(String city) {
        WeatherObservation weather = cachedWeatherProvider.getWeather(city);
        // in popular collections adding only valid cities
        if (weather != null) {
            popularCitiesService.addToPopuliarsCities(city);
        }
        return weather;
    }
}
