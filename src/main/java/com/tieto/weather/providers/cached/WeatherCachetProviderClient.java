package com.tieto.weather.providers.cached;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.providers.WeatherProvider;
import com.tieto.weather.services.PopularCitiesService;

/**
 * Cached weather provider.
 * 
 * Deals with cache and scheduled cache refreshing.
 * 
 * @author Mindaugas
 *
 */
@Component
public class WeatherCachetProviderClient implements WeatherProvider {

    private static final Logger LOG = Logger.getLogger(WeatherCachetProviderClient.class);

    @Resource(name = "weatherUndergroundClient")
    private WeatherProvider undergroundWeatherProvider;

    @Autowired
    private PopularCitiesService popularCitiesService;

    private Map<String, WeatherObservation> localCache;

    @PostConstruct
    @Scheduled(cron = "${com.tieto.cache.refresh.cron.expression}")
    public void refresh() {
        Map<String, WeatherObservation> newLocalCache = new TreeMap<String, WeatherObservation>();
        for (String city : popularCitiesService.getPopularCities()) {
            try {
                WeatherObservation weather = undergroundWeatherProvider.getWeather(city);
                if (weather != null) {
                    addToLocalCache(newLocalCache, city, weather);
                }
            } catch (RuntimeException e) {
                LOG.error(String.format("Refreshing cit=%s cache catched exception!", city), e);
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Cache reloaded, total cities in cache %d. ", newLocalCache.size()));
        }
        localCache = newLocalCache;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherObservation getWeather(final String city) {

        WeatherObservation weather = localCache.get(city.toLowerCase());
        if (weather != null) {
            return weather;
        }

        weather = undergroundWeatherProvider.getWeather(city);
        if (weather == null) {
            if (LOG.isInfoEnabled()) {
                LOG.info(String.format("Can not find information about city=%s!", city));
            }
        } else {
            addToLocalCache(city, weather);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Added city=%s, object=%s to cache.", city, weather.toString()));
        }
        return weather;
    }

    private synchronized void addToLocalCache(String city, WeatherObservation weather) {
        addToLocalCache(localCache, city, weather);
    }

    private void addToLocalCache(Map<String, WeatherObservation> cache, String city, WeatherObservation weather) {
        cache.put(city.toLowerCase(), weather);
        if (weather.getDisplayLocation() != null && StringUtils.isNotBlank(weather.getDisplayLocation().getCity())) {
            if (StringUtils.isNotBlank(weather.getDisplayLocation().getCountry())) {
                cache.put(weather.getDisplayLocation().getCountry().toLowerCase() + "/"
                        + weather.getDisplayLocation().getCity().toLowerCase(), weather);
            }
            if (StringUtils.isNotBlank(weather.getDisplayLocation().getCountryiso3166())) {
                cache.put(weather.getDisplayLocation().getCountryiso3166().toLowerCase() + "/"
                        + weather.getDisplayLocation().getCity().toLowerCase(), weather);
            }
        }
    }
}
