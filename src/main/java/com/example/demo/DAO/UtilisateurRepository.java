package com.example.demo.DAO;

import com.example.demo.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UtilisateurRepository  extends JpaRepository<Utilisateur,String> {
    public Utilisateur findByUsername(String code);
}
