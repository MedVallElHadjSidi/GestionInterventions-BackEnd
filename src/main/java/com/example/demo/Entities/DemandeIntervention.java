package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
@Entity
@Data  @NoArgsConstructor  @AllArgsConstructor @ToString
public class DemandeIntervention implements Serializable {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id_Demande;
    private  String etat_Demande;
    @Temporal(value = TemporalType.DATE)
    private Date date_Demande;
    @ManyToOne
    @JoinColumn(name = "Id_Service")
    private ServiceBMCI service;
    @ManyToOne
    @JoinColumn(name = "Code_User")
    private Utilisateur utilisateurs;
    @JsonIgnore
    @OneToMany(mappedBy = "demandeIntervention")
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
     private Collection<Espace>espaces;
    @ManyToOne
    @JoinColumn(name = "id_panne")
    private Panne panne;

    private Boolean visibiliter;


}
