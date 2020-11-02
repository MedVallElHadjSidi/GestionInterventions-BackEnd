package com.example.demo.model;

import com.example.demo.Entities.Utilisateur;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor @AllArgsConstructor
public class ModelMessage implements Serializable {

    private String nom;
    private  String message;
    private Date date;

}
