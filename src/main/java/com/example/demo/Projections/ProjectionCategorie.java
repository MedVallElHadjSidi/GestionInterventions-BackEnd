package com.example.demo.Projections;

import com.example.demo.Entities.Agence;
import com.example.demo.Entities.Categorie;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "NomCategorie",types = {Categorie.class})
public interface ProjectionCategorie {
    public String getNom();

}
