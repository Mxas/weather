package com.tieto.weather.services;

/**
 * resolves mapped object from passed
 * 
 * @author Mindaugas
 *
 */
public interface WsMapperService {

    
    /**
     * 
     * Resolves object.
     * 
     * @param param - current object
     * @param targetClass - what class object is expected 
     * @return
     */
    public <E> E resolveObject(Object param, Class<E> targetClass);
}
