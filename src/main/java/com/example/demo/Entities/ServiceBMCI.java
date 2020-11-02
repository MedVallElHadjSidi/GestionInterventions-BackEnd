package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ServiceBMCI implements Serializable {
    @Id
    private  String codeService;
    @Column(unique = true)
    private  String nom;

    @OneToMany(mappedBy = "service")
    @ToString.Exclude

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Collection<Utilisateur>utilisateurs;

    @OneToMany(mappedBy =  "service")
    @ToString.Exclude
    private  Collection<DemandeIntervention>demandeInterventions;
    public ServiceBMCI(String codeService, String nomservice, List<Utilisateur>utilisateurs){
        this.codeService=codeService;
        nom=nomservice;
        this.utilisateurs=utilisateurs;

    }



}
