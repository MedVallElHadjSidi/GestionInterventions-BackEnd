package com.example.demo.Entities;



import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity

@Data


@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Adresse implements Serializable {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long codeAdresse;
    private  String wilaye;
    private  String commune;
    private String ville;
    private String rue;

}
