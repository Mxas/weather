package com.tieto.weather.service;

import static org.junit.Assert.assertNull;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.tieto.weather.base.BaseTest;
import com.tieto.weather.mapper.WsWeatherObservationTypeMapper;
import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.model.soap.WeatherObservationType;
import com.tieto.weather.model.soap.WeatherResponse;
import com.tieto.weather.services.WsMapperService;

/**
 * Tests {@link WsMapperService}.
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class WsMapperServiceTest extends BaseTest {

    @Autowired
    private WsMapperService mapperService;

    private WeatherObservation cityVilnius;
    private WeatherObservation cityJonava;

    @Before
    public void SetUpTestData() throws Exception {
        cityVilnius = weatherTestHelper.loadFromFile("Vilnius.json").getObservation();
        cityJonava = weatherTestHelper.loadFromFile("Jonava.json").getObservation();
    }

    /** Tests {@link WsWeatherObservationTypeMapper}. */
    @Test
    public void getWsWeatherObservationTypeMapper() throws Exception {

        //this kind of mapper do not exists
        assertNull(mapperService.resolveObject(cityVilnius, Integer.class));

        weatherTestHelper.assertResult(mapperService.resolveObject(cityVilnius, WeatherObservationType.class), "Vilnius.json");
        weatherTestHelper.assertResult(mapperService.resolveObject(cityJonava, WeatherObservationType.class), "Jonava.json");

    }

    /** Tests {@link tWsWeatherResponseMapper}. */
    @Test
    public void getWsWeatherResponseMapper() throws Exception {

        WeatherResponse resonse = mapperService.resolveObject(Arrays.asList(cityVilnius, cityJonava), WeatherResponse.class);

        weatherTestHelper.assertResult(resonse.getWeatherObservation().get(0), "Vilnius.json");
        weatherTestHelper.assertResult(resonse.getWeatherObservation().get(1), "Jonava.json");

    }
}
