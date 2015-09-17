package com.tieto.weather.mapper;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.model.soap.WeatherObservationType;
import com.tieto.weather.model.soap.WeatherResponse;
import com.tieto.weather.services.WsMapperService;

/**
 * 
 * from Collection<WeatherObservation> to WeatherResponse
 *
 */
@Component
public class WsWeatherResponseMapper implements WsMapper<WeatherResponse, Collection<WeatherObservation>> {

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherResponse resolve(Collection<WeatherObservation> paramObject, WsMapperService mapperService) {
        WeatherResponse weatherResponse = new WeatherResponse();
        for (WeatherObservation weatherObservation : paramObject) {
            WeatherObservationType waetherType = mapperService.resolveObject(weatherObservation, WeatherObservationType.class);
            weatherResponse.getWeatherObservation().add(waetherType);
        }
        return weatherResponse;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<WeatherResponse> getMappedClass() {
        return WeatherResponse.class;
    }

}
