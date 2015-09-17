package com.tieto.weather.exception;

public class WeatherSearchException extends WeatherProviderException {

    private static final long serialVersionUID = -4294990274068265253L;

    public WeatherSearchException() {
    }

    public WeatherSearchException(String message) {
        super(message);
    }

    public WeatherSearchException(Throwable cause) {
        super(cause);
    }

    public WeatherSearchException(String message, Throwable cause) {
        super(message, cause);
    }

}
