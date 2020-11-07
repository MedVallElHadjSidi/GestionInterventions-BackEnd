package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ModelDemandeIntervenant {
    private Long idDemande;
    private  String intervenant;
    private  String respo;

}
