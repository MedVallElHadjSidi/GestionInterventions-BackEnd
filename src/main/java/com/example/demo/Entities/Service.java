package com.example.demo.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
@Entity @Data @NoArgsConstructor @AllArgsConstructor

public class Service implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idService;
    private  String nom;
    @OneToMany(mappedBy = "service")
    private Collection<Utilisateur>utilisateurs;
    @OneToOne
    private Utilisateur responsable;
    @OneToMany(mappedBy =  "service")
    private  Collection<DemandeIntervention>demandeInterventions;


}
