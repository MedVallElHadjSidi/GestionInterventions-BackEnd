package com.example.demo.DAO;

import com.example.demo.Entities.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AgenceRepository extends JpaRepository<Agence,Long> {
    public  Agence findByNomAgence(String nomagence);
}
