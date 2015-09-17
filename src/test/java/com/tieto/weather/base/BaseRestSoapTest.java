package com.tieto.weather.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.tieto.weather.base.BaseTest;
import com.tieto.weather.exception.WeatherAmbiguousSearchResultException;
import com.tieto.weather.exception.WeatherProviderException;
import com.tieto.weather.exception.WeatherSearchException;
import com.tieto.weather.model.soap.WeatherObservationType;
import com.tieto.weather.model.soap.WeatherResponse;
import com.tieto.weather.providers.cached.WeatherCachetProviderClient;
import com.tieto.weather.services.PopularCitiesService;

/**
 * Base test class for REST controller and SOAP endpoint
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class BaseRestSoapTest extends BaseTest {

    @Autowired
    protected PopularCitiesService popularCitiesService;

    @Autowired
    private WeatherCachetProviderClient cachedProvider;

    /** result by single city weather*/
    public abstract WeatherResponse getWeather(String city);

    /** all popular cities weather*/
    public abstract WeatherResponse getWeather();

    @Test
    public void testAllCitiesWeaterCount() {
        assertEquals(predefinedPopularCitiesCount, getWeather().getWeatherObservation().size());
    }

    @Test
    public void testDetailedCityResult() {
        List<WeatherObservationType> observations = getWeather("Vilnius").getWeatherObservation();
        assertEquals(1, observations.size());

        WeatherObservationType city = observations.get(0);

        weatherTestHelper.assertVilniusResult(city);

    }

    @Test
    public void testCached() throws Exception {

        String city = "Jonava";
        assertEquals(predefinedPopularCitiesCount, getWeather().getWeatherObservation().size());
        for (int i = 1; i < popularCitiesIndicator; i++) {
            assertNotNull(getWeather(city));
        }
        assertEquals(predefinedPopularCitiesCount, getWeather().getWeatherObservation().size());
        assertEquals(predefinedPopularCitiesCount, popularCitiesService.getPopularCities().size());

        weatherTestHelper.mockRestCallToNull(city);

        // when rest weather provider is down, controller call fails and data do
        // not goes to cache
        try {
            getWeather(city);
            fail();
        } catch (WeatherSearchException e) {
        }

        assertEquals(predefinedPopularCitiesCount, getWeather().getWeatherObservation().size());
        assertEquals(predefinedPopularCitiesCount, popularCitiesService.getPopularCities().size());

        // restoring rest providers
        weatherTestHelper.mockRestCall(city, "Jonava.json");

        assertNotNull(getWeather(city));

        assertEquals(predefinedPopularCitiesCount + 1, getWeather().getWeatherObservation().size());
        assertEquals(predefinedPopularCitiesCount + 1, popularCitiesService.getPopularCities().size());

        // data taken from cache
        weatherTestHelper.mockRestCallToNull(city);
        assertNotNull(getWeather(city));
        weatherTestHelper.assertResult(getWeather(city).getWeatherObservation().get(0), "Jonava.json");

    }

    @Test
    public void testCachedProviderRefresh() throws Exception {

        String cityJonava = "Jonava";
        assertEquals(predefinedPopularCitiesCount, getWeather().getWeatherObservation().size());
        for (int i = 1; i < popularCitiesIndicator + 1; i++) {
            weatherTestHelper.assertResult(getWeather(cityJonava).getWeatherObservation().get(0), "Jonava.json");
        }

        cachedProvider.refresh();
        weatherTestHelper.assertResult(getWeather(cityJonava).getWeatherObservation().get(0), "Jonava.json");

        // data taken from cache
        weatherTestHelper.mockRestCallToNull(cityJonava);
        weatherTestHelper.assertResult(getWeather(cityJonava).getWeatherObservation().get(0), "Jonava.json");

        //Checking that after refresh data really changes
        weatherTestHelper.mockRestCall(cityJonava, "Vilnius.json");
        cachedProvider.refresh();

        weatherTestHelper.assertResult(getWeather(cityJonava).getWeatherObservation().get(0), "Vilnius.json");

    }

    @Test
    public void testCachedProviderRefreshWhenOneCityFailsLoad() throws Exception {

        String cityJonava = "Jonava";
        assertEquals(predefinedPopularCitiesCount, getWeather().getWeatherObservation().size());
        for (int i = 1; i < popularCitiesIndicator + 1; i++) {
            weatherTestHelper.assertResult(getWeather(cityJonava).getWeatherObservation().get(0), "Jonava.json");
        }

        cachedProvider.refresh();
        weatherTestHelper.assertResult(getWeather(cityJonava).getWeatherObservation().get(0), "Jonava.json");

        // data taken from cache
        weatherTestHelper.mockRestCallToNull(cityJonava);
        //provides rest client returns null, but cached data exists
        weatherTestHelper.assertResult(getWeather(cityJonava).getWeatherObservation().get(0), "Jonava.json");

        cachedProvider.refresh();
        try {
            //search by cityJonava returns NULL
            getWeather(cityJonava);
            fail();
        } catch (WeatherSearchException e) {
        }

        //cityJonava data become reachable
        weatherTestHelper.mockRestCall(cityJonava, "Jonava.json");

        cachedProvider.refresh();
        weatherTestHelper.assertResult(getWeather(cityJonava).getWeatherObservation().get(0), "Jonava.json");

    }

    @Test
    public void getAmbiguousCity() {
        try {
            getWeather("Riga");
            Assert.fail();
        } catch (WeatherAmbiguousSearchResultException e) {

        }
    }

    @Test
    public void getUnexistingCity() {
        try {
            getWeather("Xxxx");
            Assert.fail();
        } catch (WeatherProviderException e) {

        }
    }

}
