package com.example.demo.DAO;

import com.example.demo.Entities.Espace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface EspaceRepository extends JpaRepository<Espace,Long> {

}
