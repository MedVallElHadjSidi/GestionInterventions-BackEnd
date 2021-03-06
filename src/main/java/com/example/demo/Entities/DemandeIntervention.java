package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data

@AllArgsConstructor
@ToString
public class DemandeIntervention implements Serializable {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id_Demande;
    private  String etat_Demande;
    private Date date_Demande;
    @ManyToOne
    @JoinColumn(name = "Id_Service")
    private ServiceBMCI service;
    @ManyToOne
    @JoinColumn(name = "Code_User")
    private Utilisateur utilisateurs;
    @ManyToOne
    @JoinColumn(name = "id_panne")
    private Panne panne;
    private Boolean vibinter;

    private Boolean visibiliter;
    

    
    @OneToMany(mappedBy = "demandeIntervention")
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private Collection<Intervention>interventions;
    public DemandeIntervention(){
        this.etat_Demande="initiale";
        this.date_Demande=new Date();
        this.vibinter=false;
    }




}
