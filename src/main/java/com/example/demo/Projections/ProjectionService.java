package com.example.demo.Projections;


import com.example.demo.Entities.ServiceBMCI;

import org.springframework.data.rest.core.config.Projection;

import java.util.List;


@Projection(name = "ServiceNames",types = {ServiceBMCI.class})
public interface ProjectionService {
    public String getNom();
}
