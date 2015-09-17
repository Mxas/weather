package com.tieto.weather.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tieto.weather.base.BaseRestSoapTest;
import com.tieto.weather.model.soap.WeatherResponse;

/**
 * Tests {@link WeatherController}.
 */
public class WeatherControllerTest extends BaseRestSoapTest {

    @Autowired
    private WeatherController controller;

    @Override
    public WeatherResponse getWeather(String city) {
        return controller.getWeather(city);
    }

    @Override
    public WeatherResponse getWeather() {
        return controller.getWeather();
    }

    @Test
    public void testRestSpecificSearch() throws Exception {

        weatherTestHelper.assertResult(controller.getWeather("LV", "Riga").getWeatherObservation().get(0), "LV_Riga.json");
        weatherTestHelper.assertResult(controller.getWeather("Latvia", "Riga").getWeatherObservation().get(0), "LV_Riga.json");

    }

}
