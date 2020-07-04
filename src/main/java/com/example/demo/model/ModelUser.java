package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class ModelUser {
    private  String code;
    private  String nom;
    private  String prenom;
    private  String email;
    private String username;
    private String password;
    private  String confirmation;
}
