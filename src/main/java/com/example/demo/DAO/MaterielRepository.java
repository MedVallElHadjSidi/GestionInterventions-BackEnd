package com.example.demo.DAO;

import com.example.demo.Entities.Materiel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface MaterielRepository extends JpaRepository<Materiel,Long> {
}
