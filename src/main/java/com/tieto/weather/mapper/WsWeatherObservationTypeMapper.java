package com.tieto.weather.mapper;

import org.springframework.stereotype.Component;

import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.model.soap.WeatherObservationType;
import com.tieto.weather.services.WsMapperService;

/**
 * from WeatherObservation to WeatherObservationType
 * 
 * @author Mindaugas
 *
 */
@Component
public class WsWeatherObservationTypeMapper implements WsMapper<WeatherObservationType, WeatherObservation> {
    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherObservationType resolve(WeatherObservation paramObject, WsMapperService mapperService) {
        WeatherObservationType observationType = new WeatherObservationType();
        observationType.setLocation(paramObject.getLocation());
        observationType.setRelativeHumidity(paramObject.getHumidity());
        observationType.setTempC(paramObject.getTemperature());
        observationType.setWeather(paramObject.getWeather());
        observationType.setWindDir(paramObject.getWindDirection());
        observationType.setWindString(paramObject.getWind());
        return observationType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<WeatherObservationType> getMappedClass() {
        return WeatherObservationType.class;
    }

}
