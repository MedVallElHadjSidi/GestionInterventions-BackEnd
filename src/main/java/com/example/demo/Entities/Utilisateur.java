package com.example.demo.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.naming.Name;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data  @NoArgsConstructor  @AllArgsConstructor
public  class Utilisateur implements Serializable {
    @Id
    private  String code;
    private  String nom;
    private  String email;
    @Column(unique = true)
    private String username;
    private String password;
    @ManyToOne
    @JoinColumn(name = "Agence")
    private  Agence agence;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;
    @OneToMany(mappedBy = "utilisateurs")
    private Collection<DemandeIntervention>demandeInterventions;
    @ManyToOne
    @JoinColumn(name = "service_id")
    private  Service service;
    @ManyToMany
    private  Collection<Espace>espaces;



}
