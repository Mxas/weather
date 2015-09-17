package com.tieto.weather.mapper;

import com.tieto.weather.services.WsMapperService;


/**
 * 
 * Converts mapped objects
 *
 * @param <V> - expected object type
 * @param <T> - current object class
 */
public interface WsMapper<V, T> {
    /**
     * 
     * Resolves expected object
     * 
     * @param paramObject - data
     * @param mapperService - during resolving passed {@link WsMapperService} instance
     * @return
     */
    public V resolve(T paramObject, WsMapperService mapperService);

    /**
     * indicates what class objects can by resolved
     */
    public Class<V> getMappedClass();

}
