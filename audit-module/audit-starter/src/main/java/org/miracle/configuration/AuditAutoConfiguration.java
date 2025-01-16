package org.miracle.configuration;

import org.miracle.aspect.AuditAspect;
import org.miracle.client.AuditClient;
import org.miracle.client.SessionClient;
import org.miracle.service.JWTService;
import org.miracle.service.JWTServiceImpl;
import org.miracle.utils.RsaKeyProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.servlet.http.HttpServletRequest;

@EnableFeignClients(clients = {AuditClient.class})
@Configuration
public class AuditAutoConfiguration {

    @Bean
    @ConfigurationProperties("rsa")
    @ConditionalOnProperty(prefix = "audit.jwt.service", name = "enabled", havingValue = "true")
    public RsaKeyProperties rsaKeyProperties(ResourceLoader resourceLoader) {
        return new RsaKeyProperties(resourceLoader);
    }

    @Bean
    @ConditionalOnProperty(prefix = "audit.jwt.service", name = "enabled", havingValue = "true")
    public JWTService jwtService(RsaKeyProperties rsaKeyProperties, HttpServletRequest request) {
        return new JWTServiceImpl(rsaKeyProperties, request);
    }

    @Bean
    public AuditAspect auditAspect(HttpServletRequest request, AuditClient auditClient, SessionClient sessionClient, JWTService jwtService) {
        return new AuditAspect(request, auditClient, sessionClient, jwtService);
    }

}