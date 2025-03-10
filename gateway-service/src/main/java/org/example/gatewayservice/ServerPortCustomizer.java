package org.example.gatewayservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

/**
 * @author Nursultan Abdrakypov
 **/

@Component
public class ServerPortCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Value("${server.port}")
    private int port;

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        factory.setPort(port + 3);
    }

}

