package com.example.demo.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor

public class Panne  implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPanne;
    private  String etatPanne;
    private  String Photos;
    private String description;
    @OneToMany(mappedBy = "panne")
    private List<DemandeIntervention>demandeInterventions;
    @ManyToMany()
    private Collection<Materiel>materiels;
    @ManyToOne
    private  Categorie categorie;

}
