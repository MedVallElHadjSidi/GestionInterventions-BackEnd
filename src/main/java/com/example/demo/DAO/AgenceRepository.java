package com.example.demo.DAO;

import com.example.demo.Entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgenceRepository extends JpaRepository<Agence,Long> {
    public  Agence findByNomAgence(String nomagence);
    @Query("select ag.nomAgence from Agence ag ")
    public List<String> AgenceNmes();
}
