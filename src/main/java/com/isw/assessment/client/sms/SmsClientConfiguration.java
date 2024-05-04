package com.isw.assessment.client.sms;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class SmsClientConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptorBookService(
            @Value("${service.sms.username}") String username,
            @Value("${service.sms.password}") String password) {
        return new BasicAuthRequestInterceptor(username, password);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new SmsClientFeignErrorDecoder();
    }
}
