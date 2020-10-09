package com.example.demo.DAO;

import com.example.demo.Entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    public  Categorie findByNom(String nom);

    @Query("select cat from Categorie cat where cat.nom like:x ")
    public Categorie CategorieName(@Param("x")String nom);

    @Query("select c.nom from Categorie c where c.parent is null")
    public List<String> CategorieNamesParent();
    @Query("select ca.nom from Categorie ca where ca.parent.nom like:x ")
    public List<String> SousCategorieName(@Param("x")String nom);


}
