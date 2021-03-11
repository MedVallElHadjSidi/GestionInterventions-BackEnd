package com.example.demo.DAO;

import com.example.demo.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


public interface UtilisateurRepository  extends JpaRepository<Utilisateur,String> {
    public Utilisateur findByUsername(String code);
    @Query("select distinct us from Utilisateur us where us.username like:x")
    public Utilisateur findByUserNameInter(@Param("x")String username);
    
    @Query("select distinct u from Utilisateur u where  u.agence is null")
    public List<Utilisateur> UserNamesSansRoles();






}
