package com.example.demo.model;

import lombok.*;

@Data

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnvoyerMessage {

    private  Long idespace;
    private  String  username;
    private  String message;
}
