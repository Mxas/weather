package com.tieto.weather.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.tieto.weather.mapper.WsMapper;
import com.tieto.weather.providers.restclient.WeatherUndergroundClient;

/**
 * 
 * converts from domain objects to ws objects
 *
 * @author MindaugasK
 *
 */

@Service
public class WsMapperServiceImpl implements WsMapperService {
    private static final Logger LOG = Logger.getLogger(WeatherUndergroundClient.class);

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Class<?>, WsMapper<?, ?>> mappersMap = new HashMap<Class<?>, WsMapper<?, ?>>();

    @PostConstruct
    public void initMapperService() {
        for (WsMapper<?, ?> mapper : applicationContext.getBeansOfType(WsMapper.class).values()) {
            mappersMap.put(mapper.getMappedClass(), mapper);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format("%d mapers registered", mappersMap.size()));
        }
    }

    /** {@inheritDoc} */
    @Override
    public <E> E resolveObject(Object param, Class<E> targetClass) {
        if (param == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        WsMapper<E, Object> maper = (WsMapper<E, Object>) mappersMap.get(targetClass);
        if (maper == null) {
            LOG.error(String.format("Cannot find mappee for target class: %s", targetClass));
            return null;
        }
        return maper.resolve(param, this);
    }
}
