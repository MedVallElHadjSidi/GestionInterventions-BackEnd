package com.example.demo.Entities;

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
    @OneToOne(fetch = FetchType.EAGER)
    private  Adresse adresse;
    @OneToMany(mappedBy = "agence")
    private Collection<Utilisateur> utilisateurs;



}
