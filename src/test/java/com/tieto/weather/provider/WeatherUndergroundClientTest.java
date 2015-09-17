package com.tieto.weather.provider;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tieto.weather.exception.WeatherAmbiguousSearchResultException;
import com.tieto.weather.exception.WeatherSearchException;
import com.tieto.weather.providers.restclient.WeatherUndergroundClient;

/**
 * 
 * Tests with real weather Underground info REST provider 
 *
 * @author MindaugasK
 *
 */
@Ignore("Only for developmen")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring/context.xml" })
public class WeatherUndergroundClientTest {

    @Autowired
    private WeatherUndergroundClient weatherUndergroundClient;

    @Test
    public void getSimple() {
        Assert.assertNotNull(weatherUndergroundClient.getWeather("Vilnius").getTemperature());
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
        } catch (WeatherSearchException e) {

        }
    }
}
