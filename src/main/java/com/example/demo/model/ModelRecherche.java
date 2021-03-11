package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor
public class ModelRecherche {

    private String panne;
    private String etat;
    private String datechercher;
    private String username;
    private  int page;
}
