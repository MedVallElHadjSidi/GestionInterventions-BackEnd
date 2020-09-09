package com.example.demo.webSocket;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
@CrossOrigin(origins = "*")
public class WebSocketResetController {

    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketResetController(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/hello")
    public void sendMessage(String message){
        this.template.convertAndSend("/topic/greetings",  message);
    }

}
