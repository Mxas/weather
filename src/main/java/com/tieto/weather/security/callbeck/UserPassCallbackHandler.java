package com.tieto.weather.security.callbeck;

import java.io.IOException;
import java.util.Arrays;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.security.callback.AbstractCallbackHandler;

/**
 * 
 * Validates requests username and prepares password for validation 
 *
 * @author MindaugasK
 *
 */
@Component("userPassCallbackHandler")
public class UserPassCallbackHandler extends AbstractCallbackHandler {

    @Value("${com.tieto.server.username}")
    private char[] username;

    @Value("${com.tieto.server.password}")
    private char[] password;

    @Override
    protected void handleInternal(Callback callback) throws IOException, UnsupportedCallbackException {
        if (callback instanceof WSPasswordCallback) {
            WSPasswordCallback passwordCallback = (WSPasswordCallback) callback;
            if (passwordCallback.getIdentifier() != null && Arrays.equals(username, passwordCallback.getIdentifier().toCharArray())) {
                passwordCallback.setPassword(new String(password));
            } else {
                throw new UsernameNotFoundException(passwordCallback.getIdentifier());
            }
        } else {
            throw new UnsupportedCallbackException(callback);
        }
    }
}
