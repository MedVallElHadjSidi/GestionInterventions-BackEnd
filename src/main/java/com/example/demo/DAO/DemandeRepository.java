package com.example.demo.DAO;

import com.example.demo.Entities.Categorie;
import com.example.demo.Entities.DemandeIntervention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DemandeRepository  extends JpaRepository<DemandeIntervention,Long> {
    @Query("select count(d.id_Demande) from  DemandeIntervention d,ServiceBMCI si where d.service.codeService=si.codeService and   d.visibiliter=false and si.nom ='Informatique'")
    public int NombreDeNouveauMessage();

    @Query("select de  from  DemandeIntervention de,ServiceBMCI si where de.service.codeService=si.codeService and   de.visibiliter=false and si.nom ='Informatique'")
    public List<DemandeIntervention> NouveauDemande();






}
