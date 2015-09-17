package com.tieto.weather.providers.restclient;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.tieto.weather.exception.WeatherAmbiguousSearchResultException;
import com.tieto.weather.exception.WeatherProviderException;
import com.tieto.weather.exception.WeatherSearchException;
import com.tieto.weather.model.rest.WeatherAmbiguousResult;
import com.tieto.weather.model.rest.WeatherObservation;
import com.tieto.weather.model.rest.WeatherObservationResponse;
import com.tieto.weather.model.rest.WeatherResponseInfo;
import com.tieto.weather.providers.WeatherProvider;

/**
 * Takes data from http://www.wunderground.com
 * 
 * @author Mindaugas
 *
 */
@Component
public class WeatherUndergroundClient implements WeatherProvider {

    private static final Logger LOG = Logger.getLogger(WeatherUndergroundClient.class);

    @Value("${com.tieto.wunderground.api.pattern}")
    private String wundergroundUrlPattern;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherObservation getWeather(final String city) {
        WeatherObservationResponse response = null;
        try {
            response = restTemplate.getForObject(String.format(wundergroundUrlPattern, city), WeatherObservationResponse.class);
        } catch (RestClientException e) {
            LOG.error(String.format("Search by \"%s\" failed. Server didn't respond properly", city), e);
            throw new WeatherProviderException(String.format("Search by \"%s\" failed. Server didn't respond properly", city), e);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("Search by \"%s\" returned: %s", city, response));
        }

        validate(response, city);
        return response.getObservation();
    }

    /** validating response, if needed generating info message */
    private void validate(WeatherObservationResponse response, String city) {
        if (response == null) {
            throw new WeatherSearchException(String.format("Search by \"%s\" failed. Result is null!", city));
        }

        validateResopnseInfo(response.getResponseInfo(), city);

        if (response.getObservation() == null) {
            throw new WeatherProviderException(String.format("Search by \"%s\" failed. Result current observation is null!", city));
        }
    }

    private void maeOptionsMessage(StringBuilder builder, List<WeatherAmbiguousResult> ambiguousResult) {
        boolean first = true;
        for (WeatherAmbiguousResult ambiguousCity : ambiguousResult) {
            if (!first) {
                builder.append(", ");
            } else {
                first = false;
            }
            builder.append("[try search \"").append(ambiguousCity.getCountry());
            builder.append("/").append(ambiguousCity.getName());
            builder.append("\" or with \"zmw:").append(ambiguousCity.getZmw()).append("\"]");
        }
    }

    private void validateResopnseInfo(WeatherResponseInfo info, String city) {
        if (info == null) {
            return;
        }

        if (info.getError() != null && StringUtils.isNotBlank(info.getError().getDescription())) {
            throw new WeatherSearchException(String.format("Search by \"%s\" returned error message: %s", city, info.getError()
                    .getDescription()));
        }
        if (info.getAmbiguousResult() != null && !info.getAmbiguousResult().isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("Search by \"%s\" returned %d ambiguous results. ", city, info.getAmbiguousResult().size()));
            builder.append("Possible options: ");
            maeOptionsMessage(builder, info.getAmbiguousResult());
            throw new WeatherAmbiguousSearchResultException(builder.toString());
        }
    }
}
