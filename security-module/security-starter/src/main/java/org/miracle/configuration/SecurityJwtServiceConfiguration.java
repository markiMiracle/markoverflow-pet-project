package org.miracle.configuration;

import org.miracle.filter.RootFilter;
import org.miracle.service.JWTService;
import org.miracle.service.JWTServiceImpl;
import org.miracle.utils.RsaKeyProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.servlet.http.HttpServletRequest;

@Configuration
@ConditionalOnProperty(prefix = "security.jwt.service", name = "enabled", havingValue = "true")
public class SecurityJwtServiceConfiguration {

    @Bean
    @ConfigurationProperties("rsa")
    public RsaKeyProperties rsaKeyProperties(ResourceLoader resourceLoader) {
        return new RsaKeyProperties(resourceLoader);
    }

    @Bean
    public JWTService jwtService(RsaKeyProperties rsaKeyProperties, HttpServletRequest request) {
        return new JWTServiceImpl(rsaKeyProperties, request);
    }

    @Bean
    @ConditionalOnProperty(prefix = "security.filters.root", name = "enabled", havingValue = "true")
    public FilterRegistrationBean<RootFilter> rootFilter(JWTService jwtService) {
        FilterRegistrationBean<RootFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new RootFilter(jwtService));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
