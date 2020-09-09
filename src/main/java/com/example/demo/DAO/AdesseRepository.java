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

}
