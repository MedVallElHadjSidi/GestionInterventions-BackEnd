package com.example.demo.DAO;

import com.example.demo.Entities.Intervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface InterventionRepository  extends JpaRepository<Intervention,Long> {
}
