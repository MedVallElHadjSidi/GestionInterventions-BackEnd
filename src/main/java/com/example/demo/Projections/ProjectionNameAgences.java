package com.example.demo.Projections;

import com.example.demo.Entities.Agence;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@Projection(name = "NameAgence",types = {Agence.class})
public interface ProjectionNameAgences {
        public String getNomAgence();

}
