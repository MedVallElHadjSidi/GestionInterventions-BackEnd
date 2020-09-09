package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Agence  implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;
    @Column(unique = true)
    private String nomAgence;
    @OneToOne(cascade = {CascadeType.ALL})
    private  Adresse adresse;
    @OneToMany(mappedBy = "agence")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Utilisateur> utilisateurs;
    public  Agence(String nomAgence,Adresse adresse){
        this.nomAgence=nomAgence;
        this.adresse =adresse;
    }



}
