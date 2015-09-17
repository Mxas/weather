package com.tieto.weather.endpoint;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tieto.weather.base.BaseRestSoapTest;
import com.tieto.weather.model.soap.WeatherRequest;
import com.tieto.weather.model.soap.WeatherResponse;

/**
 * Tests {@link WeatherEndpoint}.
 */
public class WeatherEndpointTest extends BaseRestSoapTest {

    @Autowired
    private WeatherEndpoint endpoint;

    @Override
    public WeatherResponse getWeather(String city) {
        WeatherRequest request = new WeatherRequest();
        request.getCity().add(city);
        return endpoint.getWeather(request);
    }

    @Override
    public WeatherResponse getWeather() {
        WeatherRequest request = new WeatherRequest();
        request.getCity().addAll(popularCitiesService.getPopularCities());
        return endpoint.getWeather(request);
    }

    @Test
    public void testSoapSpecificSearch() {
        WeatherRequest request = new WeatherRequest();
        request.getCity().add("Vilnius");
        request.getCity().add("Jonava");
        WeatherResponse responce = endpoint.getWeather(request);
        assertEquals(2, responce.getWeatherObservation().size());
    }

}
