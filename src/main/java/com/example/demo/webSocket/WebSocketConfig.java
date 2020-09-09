package com.example.demo.webSocket;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@ConfigurationProperties
@EnableWebSocketMessageBroker
@EnableConfigurationProperties(WebSocketProperties.class)
@AllArgsConstructor
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    private WebSocketProperties properties;

        @Override
        public void configureMessageBroker(MessageBrokerRegistry config) {

            config.enableSimpleBroker(properties.getTopicPrefix());
            config.setApplicationDestinationPrefixes(properties.getApplicationPrefix());
        }

        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint(properties.getEndpoint()).setAllowedOrigins("http://localhost:4200").withSockJS();
        }


}