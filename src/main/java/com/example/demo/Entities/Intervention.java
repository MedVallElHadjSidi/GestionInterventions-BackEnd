package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Data


@AllArgsConstructor
@ToString
public class Intervention implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idIntervention;
    private double dureIntervention;
    private  String etatIntervention;
    @Temporal(value = TemporalType.TIME)
    private Date dred;
    
    @OneToOne
    private  Espace espace;

    @ManyToOne
    @JoinColumn(name = "Demande_ID")
    @JsonIgnore
    private  DemandeIntervention demandeIntervention;

    public  Intervention(){
        this.etatIntervention="EnCours";
    }
}
