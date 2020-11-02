package com.example.demo.Entities;

import com.example.demo.model.ModelMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data


@AllArgsConstructor
@ToString

public class Espace implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idEspace;
    private String etatEspace;
    @Lob
    private LinkedList<ModelMessage> commentaire=new LinkedList<>();
    @OneToOne
    @JsonIgnore
    private  Intervention intervention;
    @ManyToMany
    @JoinTable( name = "T_Users_Espace_Associations",
            joinColumns = @JoinColumn( name = "idEspace" ),
            inverseJoinColumns = @JoinColumn( name = "code" ) )
    private Collection<Utilisateur>utilisateurs;

    public Espace() {
        this.etatEspace="ouvert";
    }
}
