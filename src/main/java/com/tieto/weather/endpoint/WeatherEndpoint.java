package com.tieto.weather.endpoint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.tieto.weather.exception.WeatherSearchException;
import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.model.soap.WeatherRequest;
import com.tieto.weather.model.soap.WeatherResponse;
import com.tieto.weather.services.RepositoryService;
import com.tieto.weather.services.WsMapperService;

/**
 * Weather SOAP endpoint.
 */
@Endpoint
public class WeatherEndpoint {

    private static final String NAMESPACE_URI = "http://tieto.com/weather/schemas";

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private WsMapperService mapperService;

    /**
     * Endpoint for fetching location weather data.
     *
     * @param {@link WeatherRequest}
     * @return {@link WeatherResponse}
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "WeatherRequest")
    @ResponsePayload
    public WeatherResponse getWeather(@RequestPayload final WeatherRequest request) {

        if (request == null) {
            throw new WeatherSearchException("Not provided search parameter!");
        }
        Collection<WeatherObservation> collections = new ArrayList<WeatherObservation>();
        Set<String> set = new HashSet<String>(request.getCity());
        for (String city : set) {
            if (StringUtils.isBlank(city)) {
                throw new WeatherSearchException("Not provided city parameter!");
            }
            collections.add(repositoryService.getWeather(city));
        }
        return mapperService.resolveObject(collections, WeatherResponse.class);
    }
}
