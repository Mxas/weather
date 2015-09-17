package com.tieto.weather.services;

import java.util.Collection;

/**
 * Popular cities service.
 * 
 * Analyses requests by cities and return popular cities collection;
 *
 * @author MindaugasK
 *
 */
public interface PopularCitiesService {

    /**
     * How to handle search {@link SearchType#REGULAR},
     * {@link SearchType#CACHED}, {@link SearchType#ADD}
     */
    public enum SearchType {
        /** search wather using regular service */
        REGULAR,
        /** take wather from cached service */
        CACHED,
        /** needs add ciyti to cache */
        ADD
    }

    /**
     * Popular cities that are requested for more than nn times during lifetime
     * of application and taken from configuration
     * 
     * @return Collection, never null
     */
    public Collection<String> getPopularCities();

    /**
     * Audits cities that are requested be clients. If needed city is added to
     * Popular Cities collections.
     * 
     * @param city
     *            - requested city
     * @return - {@link SearchType}
     */
    public SearchType analizeSearch(String city);

    /**
     * Adds to popular cities collections
     * 
     * @param city
     */
    public void addToPopuliarsCities(String city);

}
