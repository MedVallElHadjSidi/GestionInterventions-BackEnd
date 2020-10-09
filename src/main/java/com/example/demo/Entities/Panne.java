package com.example.demo.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@Entity
@Data  @NoArgsConstructor  @AllArgsConstructor @ToString
public class Panne  implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPanne;
    private  String etatPanne;
    @Lob
    private byte[] Photos;
    private String description;
    private  String imageString;




    @OneToMany(mappedBy = "panne")
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

    private List<DemandeIntervention>demandeInterventions;

    @ManyToMany()
    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Collection<Materiel>materiels;
    @ManyToOne
    private  Categorie categorie;

}
