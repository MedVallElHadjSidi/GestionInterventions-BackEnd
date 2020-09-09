package com.example.demo.DAO;

import com.example.demo.Entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RepositoryRestResource
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    @Query("select cat from Categorie cat where cat.nom like:x ")
    public Categorie CategorieName(@Param("x")String nom);
}
