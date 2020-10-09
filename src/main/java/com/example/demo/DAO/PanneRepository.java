package com.example.demo.DAO;

import com.example.demo.Entities.Panne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


public interface PanneRepository extends JpaRepository<Panne,Long> {
}
