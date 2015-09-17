package com.tieto.weather.controller;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.model.soap.WeatherResponse;
import com.tieto.weather.services.RepositoryService;
import com.tieto.weather.services.WsMapperService;

/**
 * Weather Rest controller.
 */
@Controller
public class WeatherController {

    private static final String HEADER = "Accept=application/json";

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private WsMapperService mapperService;

    /**
     * Endpoint for fetching supported locations weather data.
     *
     * @return {@link WeatherResponse}
     */
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET, headers = HEADER)
    public WeatherResponse getWeather() {
        Collection<WeatherObservation> allWeatherObservation = repositoryService.getWeather();
        return mapperService.resolveObject(allWeatherObservation, WeatherResponse.class);
    }

    /**
     * Endpoint for fetching single location weather data.
     *
     * @return {@link WeatherResponse}
     */
    @ResponseBody
    @RequestMapping(value = "/{city}", method = RequestMethod.GET, headers = HEADER)
    public WeatherResponse getWeather(@PathVariable final String city) {
        WeatherObservation weatherObservation = repositoryService.getWeather(city);
        return mapperService.resolveObject(Arrays.asList(weatherObservation), WeatherResponse.class);
    }

    /**
     * Endpoint for fetching single location weather data.
     *
     * @return {@link WeatherResponse}
     */
    @ResponseBody
    @RequestMapping(value = "/{contry}/{city}", method = RequestMethod.GET, headers = HEADER)
    public WeatherResponse getWeather(@PathVariable final String contry, @PathVariable final String city) {
        WeatherObservation weatherObservation = repositoryService.getWeather(contry + "/" + city);
        return mapperService.resolveObject(Arrays.asList(weatherObservation), WeatherResponse.class);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleTypeMismatchException(Exception ex) {
        return ex.getMessage();
    }

}
