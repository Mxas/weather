package com.tieto.weather.helper;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.model.rest.WeatherObservationResponse;
import com.tieto.weather.model.soap.WeatherObservationType;

/**
 * 
 * Prepares test data for mock spring beans
 * Some assertion methods
 * 
 * @author MindaugasK
 *
 */
public class WeatherTestHelper implements InitializingBean {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${com.tieto.wunderground.api.pattern}")
    private String wundergroundUrlPattern;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterPropertiesSet() throws Exception {

        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, "Helsinki"), WeatherObservationResponse.class)).thenReturn(
                loadFromFile("Helsinki.json"));
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, "LV/Riga"), WeatherObservationResponse.class)).thenReturn(
                loadFromFile("LV_Riga.json"));
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, "Latvia/Riga"), WeatherObservationResponse.class)).thenReturn(
                loadFromFile("LV_Riga.json"));
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, "Riga"), WeatherObservationResponse.class)).thenReturn(loadFromFile("Riga.json"));
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, "Tallinn"), WeatherObservationResponse.class)).thenReturn(
                loadFromFile("Tallinn.json"));
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, "Vilnius"), WeatherObservationResponse.class)).thenReturn(
                loadFromFile("Vilnius.json"));
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, "Xxxx"), WeatherObservationResponse.class)).thenReturn(loadFromFile("Xxxx.json"));
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, "Jonava"), WeatherObservationResponse.class)).thenReturn(
                loadFromFile("Jonava.json"));
    }

    public WeatherObservationResponse loadFromFile(String filename) throws Exception {
        return mapper.readValue(new ClassPathResource("data/" + filename).getInputStream(), WeatherObservationResponse.class);
    }

    /**
     * asserts arguments with predefined test data
     */
    public void assertResult(WeatherObservationType argument, String filename) throws Exception {
        WeatherObservation observation = loadFromFile(filename).getObservation();

        assertEquals(observation.getLocation(), argument.getLocation());
        assertEquals(observation.getHumidity(), argument.getRelativeHumidity());
        assertEquals(observation.getTemperature(), argument.getTempC(), 0);
        assertEquals(observation.getWeather(), argument.getWeather());
        assertEquals(observation.getWindDirection(), argument.getWindDir());
        assertEquals(observation.getWind(), argument.getWindString());
    }

    public void assertObservation(WeatherObservation argument, String filename) throws Exception {
        WeatherObservation observation = loadFromFile(filename).getObservation();

        assertEquals(observation.getLocation(), argument.getLocation());
        assertEquals(observation.getHumidity(), argument.getHumidity());
        assertEquals(observation.getTemperature(), argument.getTemperature(), 0);
        assertEquals(observation.getWeather(), argument.getWeather());
        assertEquals(observation.getWindDirection(), argument.getWindDirection());
        assertEquals(observation.getWind(), argument.getWind());
    }

    /** asserts arguments with predefined Vilnius test data */
    public void assertVilniusResult(WeatherObservationType argument) {
        assertEquals("Europe/Vilnius", argument.getLocation());
        assertEquals("58%", argument.getRelativeHumidity());
        assertEquals(12.0, argument.getTempC(), 0);
        assertEquals("Clear", argument.getWeather());
        assertEquals("SE", argument.getWindDir());
        assertEquals("From the SE at 17 MPH", argument.getWindString());
    }

    public void mockRestCall(String city, String fileName) throws Exception {
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, city), WeatherObservationResponse.class)).thenReturn(loadFromFile(fileName));
    }

    public void mockRestCallToNull(String city) throws Exception {
        when(restTemplate.getForObject(String.format(wundergroundUrlPattern, city), WeatherObservationResponse.class)).thenReturn(null);
    }
}
