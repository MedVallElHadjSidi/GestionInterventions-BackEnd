package com.example.demo.Entities;

import com.example.demo.model.ModelMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Espace implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idEspace;
    @Transient
    private List<ModelMessage> commentaire;
    @OneToOne
    private  Intervention intervention;


    @ManyToMany
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Utilisateur>utilisateurs;



}
