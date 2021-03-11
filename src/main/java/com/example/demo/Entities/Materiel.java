package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Materiel implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idMateriel;
    @Column(unique = true)
    private  String nom;
    private  String model;
    private  String processeur;
   /* @ManyToMany()
    @JoinTable( name = "T_Panne_Materiels",
            joinColumns = @JoinColumn( name = "idMateriel" ),
            inverseJoinColumns = @JoinColumn( name = "idPanne" ) )

    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Panne>pannes;*/
    @ManyToOne
    @JoinColumn(name = "Agence")
    private Agence agence;
    @OneToMany(mappedBy = "materiel")
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Materiel_Panne>materiel_Pannes;
}
