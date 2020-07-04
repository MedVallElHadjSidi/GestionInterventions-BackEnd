package com.example.demo.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Espace implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idEspace;
    private  String commentaire;
    @ManyToMany
    private Collection<Utilisateur>utilisateurs;
    @ManyToOne
    @JoinColumn(name = "ID_Demande")
    private  DemandeIntervention demandeIntervention;
}
