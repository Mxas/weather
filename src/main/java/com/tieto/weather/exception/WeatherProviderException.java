package com.tieto.weather.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.SERVER)
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class WeatherProviderException extends RuntimeException {

    private static final long serialVersionUID = -3576602471189509598L;

    public WeatherProviderException() {
    }

    public WeatherProviderException(String message) {
        super(message);
    }

    public WeatherProviderException(Throwable cause) {
        super(cause);
    }

    public WeatherProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
