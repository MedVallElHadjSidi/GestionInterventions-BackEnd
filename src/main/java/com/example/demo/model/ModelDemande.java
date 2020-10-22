package com.example.demo.model;


import lombok.*;


@Data
@NoArgsConstructor  @AllArgsConstructor
public class ModelDemande {
    private  String type;
    private String etat;
    private String materiel;
    private String description;
    private String image;
    private String service;
    private String userDemander;



}
