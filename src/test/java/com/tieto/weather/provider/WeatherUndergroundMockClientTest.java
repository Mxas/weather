package com.tieto.weather.provider;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tieto.weather.base.BaseTest;
import com.tieto.weather.exception.WeatherAmbiguousSearchResultException;
import com.tieto.weather.exception.WeatherProviderException;
import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.providers.restclient.WeatherUndergroundClient;

/**
 * Testing {@link WeatherUndergroundClient} with mock REST http template service
 */
public class WeatherUndergroundMockClientTest extends BaseTest {

    @Autowired
    private WeatherUndergroundClient weatherUndergroundClient;

    @Test
    public void getSimple() {
        WeatherObservation weather = weatherUndergroundClient.getWeather("Vilnius");
        Assert.assertNotNull(weather);
        Assert.assertEquals("Europe/Vilnius", weather.getLocation());
    }

    @Test
    public void getAmbiguousCity() {
        try {
            weatherUndergroundClient.getWeather("Riga");
            Assert.fail();
        } catch (WeatherAmbiguousSearchResultException e) {

        }
    }

    @Test
    public void getUnexistingCity() {
        try {
            weatherUndergroundClient.getWeather("Xxxx");
            Assert.fail();
        } catch (WeatherProviderException e) {

        }
    }
}
