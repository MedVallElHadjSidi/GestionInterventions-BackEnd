package com.example.demo.Entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
@Entity @Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Categorie  implements Serializable {
    @Id   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idCategorie;
    private  String nom;
    @OneToOne

    private Categorie parent;
}
