package com.example.demo.webSocket;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

@ConfigurationProperties("app.websocket")
public class WebSocketProperties {
    private String applicationPrefix = "/app";
    /**
     * Prefix used by topics
     */
    private String topicPrefix = "/topic";
    /**
     * Endpoint that can be used to connect to
     */
    private String endpoint = "/ws";
    /**
     * Allowed origins
     */
    private String[] allowedOrigins = new String[0];
}
