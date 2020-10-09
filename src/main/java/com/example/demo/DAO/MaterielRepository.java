package com.example.demo.DAO;

import com.example.demo.Entities.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


public interface MaterielRepository extends JpaRepository<Materiel,Long> {
    public Materiel findByNom(String nom);


    @Query("select m.nom from Materiel m, Utilisateur u ,Agence  ag where ag.idAgence=m.agence.idAgence and u.agence.idAgence=ag.idAgence and u.username like:x ")
    public List<String> MaterielsNames(@Param("x")String username);
}
