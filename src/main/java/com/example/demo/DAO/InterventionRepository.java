package com.example.demo.DAO;


import com.example.demo.Entities.Intervention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


public interface InterventionRepository  extends JpaRepository<Intervention,Long> {
    @Query("select count(ir.idIntervention) From Intervention ir, Espace er    where   ir.idIntervention=er.intervention.idIntervention and ir.etatIntervention='Reussie' " )
    public int NmobreServcieResolu();
    @Query("select count(ir.idIntervention) From Intervention ir, Espace er    where   ir.idIntervention=er.intervention.idIntervention and ir.etatIntervention='Non Resolu' " )
    public int NmobreServcieNonResolu();

    @Query("select count(ir.idIntervention) From Intervention ir, Espace er    where   ir.idIntervention=er.intervention.idIntervention and ir.etatIntervention='EnCours' " )
    public int NmobreServcieEnCours();
    
    public Page<Intervention> findByEtatIntervention(String intervention,Pageable pageable);


}
