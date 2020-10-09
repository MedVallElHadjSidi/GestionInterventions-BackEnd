package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Espace implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idEspace;
    private  String commentaire;
    @ManyToMany
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Utilisateur>utilisateurs;
    @ManyToOne
    @JoinColumn(name = "ID_Demande")
    private  DemandeIntervention demandeIntervention;
}
