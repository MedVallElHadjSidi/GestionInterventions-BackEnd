package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public  class Utilisateur implements Serializable {
    @Id
    private  String code;
    private  String nom;
    private  String email;
    @Column(unique = true)
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    @ManyToOne
    @JoinColumn(name = "Agence")
    private  Agence agence;

    @JsonIgnore
    @OneToMany(mappedBy = "utilisateurs")
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<DemandeIntervention>demandeInterventions;

    @ManyToOne
    @JoinColumn(name = "Service_ID")

    private ServiceBMCI service;
    @JsonIgnore
    @ManyToMany


    private  Collection<Espace>espaces;



}
