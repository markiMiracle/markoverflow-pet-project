package org.miracle.configuration;


import org.miracle.client.SessionClient;
import org.miracle.filter.LoggingFilter;
import org.miracle.filter.RootFilter;
import org.miracle.filter.SessionFilter;
import org.miracle.filter.UserFilter;
import org.miracle.service.JWTServiceImpl;
import org.miracle.service.JWTService;
import org.miracle.service.SessionCashService;
import org.miracle.service.SessionCashServiceImpl;
import org.miracle.utils.RsaKeyProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import javax.servlet.http.HttpServletRequest;


@EnableFeignClients(clients = SessionClient.class)
@Configuration
public class SecurityAutoConfiguration {

    @Bean
    public SessionCashService sessionCashService(SessionClient sessionClient, HttpServletRequest request) {
        return new SessionCashServiceImpl(sessionClient, request);
    }

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    @ConditionalOnProperty(prefix = "security.filters.user", name = "enabled", havingValue = "true")
    public FilterRegistrationBean<UserFilter> userFilter() {
        FilterRegistrationBean<UserFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    @ConditionalOnProperty(prefix = "security.filters.session", name = "enabled", havingValue = "true")
    public FilterRegistrationBean<SessionFilter> sessionFilter(SessionCashService sessionService) {
        FilterRegistrationBean<SessionFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionFilter(sessionService));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

}
