package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;


@Data  @NoArgsConstructor  @AllArgsConstructor
public class ModelDemande {
    private  String type;
    private String etat;
    private String materiel;
    private String description;
    private String image;
    private String service;
    private String userDemander;



}
