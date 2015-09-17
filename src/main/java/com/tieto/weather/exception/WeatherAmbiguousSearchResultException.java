package com.tieto.weather.exception;

public class WeatherAmbiguousSearchResultException extends WeatherProviderException {

    private static final long serialVersionUID = -4294990274068265253L;

    public WeatherAmbiguousSearchResultException() {
    }

    public WeatherAmbiguousSearchResultException(String message) {
        super(message);
    }

    public WeatherAmbiguousSearchResultException(Throwable cause) {
        super(cause);
    }

    public WeatherAmbiguousSearchResultException(String message, Throwable cause) {
        super(message, cause);
    }

}
