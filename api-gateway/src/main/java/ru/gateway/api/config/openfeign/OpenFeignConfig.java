package ru.gateway.api.config.openfeign;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenFeignConfig {

    @Bean
    public Encoder multipartFormEncoder(@Autowired RestTemplate restTemplate) {
        return new SpringFormEncoder(new SpringEncoder(() -> new HttpMessageConverters(restTemplate.getMessageConverters())));
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        HttpClient httpClient = HttpClients.custom()
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }

}
