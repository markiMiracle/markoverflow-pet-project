package org.miracle.configuration;


import org.miracle.client.SessionClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "security.session.client", name = "enabled", havingValue = "true")
@EnableFeignClients(clients = {SessionClient.class})
public class SecuritySessionClient {
}
