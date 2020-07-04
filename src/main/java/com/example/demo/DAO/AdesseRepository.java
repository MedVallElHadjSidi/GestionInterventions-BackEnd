package com.example.demo.DAO;

import com.example.demo.Entities.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AdesseRepository extends JpaRepository<Adresse,Long> {
}
