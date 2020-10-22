package com.example.demo.DAO;

import com.example.demo.Entities.Adresse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AdesseRepository extends JpaRepository<Adresse,String> {
    @Query("select a.codeAdresse from Adresse a ")
    public List<String> CodeAdresses();
    @Query("select ad from Adresse ad where ad.wilaye like:d and ad.commune like:y and ad.ville like:z and ad.rue like:w" )
    public Adresse ChercherAdresse(@Param("d")String wilaye,@Param("y")String commune,@Param("z")String ville,@Param("w") String rue);

}
