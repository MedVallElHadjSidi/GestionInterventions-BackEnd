package com.example.demo.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Intervention implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idIntervention;
    private int dureIntervention;
    private  String EtatIntervention;
    @ManyToOne
    @JoinColumn(name = "ID_Demande")
    private  DemandeIntervention demandeIntervention;
}
