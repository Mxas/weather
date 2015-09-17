package com.tieto.weather.service;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import com.tieto.weather.base.BaseTest;
import com.tieto.weather.controller.WeatherController;
import com.tieto.weather.services.PopularCitiesService;
import com.tieto.weather.services.PopularCitiesService.SearchType;

/**
 * Tests {@link WeatherController}.
 */
public class PopularCitiesServiceTest extends BaseTest {

    @Autowired
    private PopularCitiesService popularCitiesService;

    @Test
    public void getInitPopulatList() {
        Assert.assertTrue(popularCitiesIndicator > 0);
        Assert.assertTrue(predefinedPopularCitiesCount > 0);

        Assert.assertEquals(predefinedPopularCitiesCount, popularCitiesService.getPopularCities().size());
    }

    @Test
    @DirtiesContext
    public void getIncreaseAccessCountAndAnalize() {
        String city = "Test1";
        for (int i = 1; i < popularCitiesIndicator; i++) {
            Assert.assertEquals(SearchType.REGULAR, popularCitiesService.analizeSearch(city));
        }

        Assert.assertEquals(predefinedPopularCitiesCount, popularCitiesService.getPopularCities().size());
        Assert.assertFalse(popularCitiesService.getPopularCities().contains(city));

        //Adding current with gives true cach city
        Assert.assertEquals(SearchType.ADD, popularCitiesService.analizeSearch(city));
        popularCitiesService.addToPopuliarsCities(city);

        Assert.assertEquals(predefinedPopularCitiesCount + 1, popularCitiesService.getPopularCities().size());
        Assert.assertTrue(popularCitiesService.getPopularCities().contains(city));

        //futher adding gives false
        Assert.assertEquals(SearchType.CACHED, popularCitiesService.analizeSearch(city));

        Assert.assertEquals(predefinedPopularCitiesCount + 1, popularCitiesService.getPopularCities().size());
        Assert.assertTrue(popularCitiesService.getPopularCities().contains(city));

    }
}
