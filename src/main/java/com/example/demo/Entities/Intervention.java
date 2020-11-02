package com.example.demo.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Intervention implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idIntervention;
    private int dureIntervention;
    private  String EtatIntervention;
    
    @OneToOne
    private  Espace espace;

    @ManyToOne
    @JoinColumn(name = "Demande_ID")
    @JsonIgnore
    private  DemandeIntervention demandeIntervention;


}
