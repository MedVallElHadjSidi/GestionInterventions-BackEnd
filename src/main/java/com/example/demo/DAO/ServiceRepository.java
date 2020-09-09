package com.example.demo.DAO;

import com.example.demo.Entities.Categorie;
import com.example.demo.Entities.ServiceBMCI;
import com.example.demo.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ServiceRepository extends JpaRepository<ServiceBMCI,String> {

    public ServiceBMCI findByNom(String nom);

    @Query("select distinct se.nom from ServiceBMCI se ")
    public List<String> ServiceNames();

    @Query("select distinct ser from ServiceBMCI ser   ")
    public List<ServiceBMCI> ServiceNamesSansAdmi();



}
