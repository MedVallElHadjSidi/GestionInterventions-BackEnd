package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class DemandeNotifier {
    private  Long id_Demande;
    private  String utilisateur;
    private String etatpanne;
    private String nomAgence;



}
