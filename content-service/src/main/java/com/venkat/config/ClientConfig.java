package com.venkat.config;

import com.venkat.service.PostClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(ClientConfig.class);

    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    PostClient postClient(){
        logger.info("Configuration for WebClient to connect another restful webservices");
        WebClient client = WebClient.builder()
                .baseUrl("http://post-service/api") //service name is used
                .filter(filterFunction)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                        .builderFor(WebClientAdapter.create(client))
                        .build();
        return factory.createClient(PostClient.class);
    }
}
