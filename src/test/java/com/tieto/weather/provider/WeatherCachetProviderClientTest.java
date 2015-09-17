package com.tieto.weather.provider;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.tieto.weather.base.BaseTest;
import com.tieto.weather.exception.WeatherSearchException;
import com.tieto.weather.providers.cached.WeatherCachetProviderClient;
import com.tieto.weather.providers.restclient.WeatherUndergroundClient;

/**
 * Testing {@link WeatherUndergroundClient} with mock REST http template service
 */

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class WeatherCachetProviderClientTest extends BaseTest {

    @Autowired
    private WeatherCachetProviderClient cachetProvider;

    @Test
    public void getScheduleRefreshRefresk() throws Exception {

        String cityJonava = "Jonava";
        for (int i = 1; i < popularCitiesIndicator + 1; i++) {
            weatherTestHelper.assertObservation(cachetProvider.getWeather(cityJonava), "Jonava.json");
        }

        weatherTestHelper.assertObservation(cachetProvider.getWeather(cityJonava), "Jonava.json");

        // data taken from cache
        weatherTestHelper.mockRestCallToNull(cityJonava);
        weatherTestHelper.assertObservation(cachetProvider.getWeather(cityJonava), "Jonava.json");

        Thread.sleep(4000);

        try {// after refresh Janava city can not be accessed
            cachetProvider.getWeather(cityJonava);
            fail();
        } catch (WeatherSearchException e) {
        }

        weatherTestHelper.mockRestCall(cityJonava, "Vilnius.json");
        Thread.sleep(4000);
        weatherTestHelper.assertObservation(cachetProvider.getWeather(cityJonava), "Vilnius.json");

    }

}
