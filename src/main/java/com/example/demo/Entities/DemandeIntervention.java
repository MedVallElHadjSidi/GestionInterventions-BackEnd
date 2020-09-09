package com.example.demo.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
@Entity @NoArgsConstructor @AllArgsConstructor
@Data
public class DemandeIntervention implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id_Demande;
    private  String Etat_Demande;
    @Temporal(value = TemporalType.DATE)
    private Date date_Demande;
    @ManyToOne
    @JoinColumn(name = "Id_Service")
    private ServiceBMCI service;
    @ManyToOne
    @JoinColumn(name = "Code_User")
    private Utilisateur utilisateurs;
    @OneToMany(mappedBy = "demandeIntervention")
    private Collection<Espace>espaces;
    @ManyToOne
    @JoinColumn(name = "id_panne")
    private Panne panne;
    @OneToMany(mappedBy = "demandeIntervention")
    private  Collection<Intervention>interventions;


}
