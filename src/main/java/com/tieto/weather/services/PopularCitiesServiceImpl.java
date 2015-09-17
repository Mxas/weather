package com.tieto.weather.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** main {@link PopularCitiesService} implementation */
@Service
public class PopularCitiesServiceImpl implements PopularCitiesService {

    @Value("${com.tieto.popular.cities.indicator}")
    private int popularCitiesIndicator;

    @Value("#{'${com.tieto.predefined.popular.cities}'.split(',')}")
    private Collection<String> predefinedPopularCities;

    private Collection<String> popularCities;
    private Map<String, Integer> citiesAccessCount;

    @PostConstruct
    public void initConfiguration() {
        popularCities = new TreeSet<String>(predefinedPopularCities);
        citiesAccessCount = new HashMap<String, Integer>();
    }

    /** {@inheritDoc} */
    public Collection<String> getPopularCities() {
        return popularCities;
    }

    /** {@inheritDoc} */
    public SearchType analizeSearch(String city) {
        if (popularCities.contains(city)) {
            return SearchType.CACHED;
        }
        int currentCount = increaseAccessCount(city);
        if (currentCount >= popularCitiesIndicator) {

            return SearchType.ADD;
        }
        return SearchType.REGULAR;
    }

    public synchronized void addToPopuliarsCities(String city) {
        popularCities.add(city);
    }

    private synchronized int increaseAccessCount(String city) {
        Integer current = citiesAccessCount.get(city);
        if (current == null) {
            current = 1;
        } else {
            current++;
        }
        citiesAccessCount.put(city, current);
        return current;
    }

}
