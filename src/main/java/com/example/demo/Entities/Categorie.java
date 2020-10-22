package com.example.demo.Entities;



import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Categorie  implements Serializable {
    @Id   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idCategorie;
    private  String nom;
    @OneToOne
    private Categorie parent;
}
