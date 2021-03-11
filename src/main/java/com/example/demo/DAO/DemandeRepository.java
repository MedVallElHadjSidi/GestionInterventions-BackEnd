package com.example.demo.DAO;

import com.example.demo.Entities.Categorie;
import com.example.demo.Entities.DemandeIntervention;
import com.example.demo.Entities.Intervention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;
@Repository
@CrossOrigin(origins = "*",allowedHeaders = "*")
public interface DemandeRepository  extends JpaRepository<DemandeIntervention,Long> {
    
    @Query("select count(d.id_Demande) from  DemandeIntervention d,ServiceBMCI si where d.service.codeService=si.codeService and   d.visibiliter=false and si.nom ='Informatique'")
    public int NombreDeNouveauMessage();

    @Query("select de  from  DemandeIntervention de,ServiceBMCI si where de.service.codeService=si.codeService and   de.visibiliter=false and de.etat_Demande!='Rejeter' and si.nom ='Informatique'")
    public List<DemandeIntervention> NouveauDemande();

    @Query("select  dc  from  DemandeIntervention dc,ServiceBMCI sin where dc.service.codeService=sin.codeService and  dc.etat_Demande='EnCours' and  dc.visibiliter=true and sin.nom ='Informatique' ")
    public List<DemandeIntervention> DemandeEnCours();

    @Query("select di  from  DemandeIntervention di,ServiceBMCI si,Utilisateur ut where di.utilisateurs.code=ut.code and di.service.codeService=si.codeService and di.utilisateurs.username =:us and  di.etat_Demande='EnCours' and  di.visibiliter=true and si.nom ='Informatique' ")
    public Page<DemandeIntervention> DemandeUsersEnCours(@Param("us") String username, Pageable pageable);

    @Query("select dm  from  DemandeIntervention dm,ServiceBMCI sn,Utilisateur us where dm.utilisateurs.code=us.code and dm.service.codeService=sn.codeService and dm.utilisateurs.username =:un and  dm.etat_Demande='Rejeter' and  dm.visibiliter=true and sn.nom ='Informatique' ")
    public Page<DemandeIntervention> DemandeUsersRejeter(@Param("un") String username,Pageable pageable);
    @Query("select drj  from  DemandeIntervention drj,ServiceBMCI srj,Utilisateur urj where drj.utilisateurs.code=urj.code and drj.service.codeService=srj.codeService and drj.utilisateurs.username =:un and  drj.etat_Demande='Rejeter' and  drj.visibiliter=false and srj.nom ='Informatique' ")
    public Page<DemandeIntervention> DemandeUsersRejeterNotifier(@Param("un") String username,Pageable pageable);

    @Query("select dsr From  Intervention sr,Espace e ,DemandeIntervention dsr  where   sr.idIntervention=e.intervention.idIntervention and dsr.id_Demande =sr.demandeIntervention.id_Demande and sr.etatIntervention='Reussie' and dsr.etat_Demande='Resolu'  ")
    public Page<DemandeIntervention>ServiceResolu(Pageable pageable);
    
    
    @Query("select dsr From  Intervention sr,Espace e ,DemandeIntervention dsr  where   sr.idIntervention=e.intervention.idIntervention and dsr.id_Demande =sr.demandeIntervention.id_Demande and sr.etatIntervention='Non Resolu' and dsr.etat_Demande='Non Resolu'  ")
    public Page<DemandeIntervention>ServiceNonResolu(Pageable pageable);
    
    @Query("select dsr From  Intervention sr,Espace e ,DemandeIntervention dsr  where   sr.idIntervention=e.intervention.idIntervention and dsr.id_Demande =sr.demandeIntervention.id_Demande and dsr.etat_Demande!='Rejeter'  and dsr.etat_Demande!='initiale'  ")
    public Page<DemandeIntervention>ConsulterHistoriqueServicesInfo(Pageable pageable);
    

    @Query("select  d  from  DemandeIntervention d,ServiceBMCI s,Utilisateur u,Intervention i where i.demandeIntervention.id_Demande=d.id_Demande and i.etatIntervention='Reussie'  and d.utilisateurs.code=u.code and d.service.codeService=s.codeService and d.utilisateurs.username =:usr  and  d.visibiliter=true and s.nom ='Informatique' ")
    public Page<DemandeIntervention>DemandeUsersResolu(@Param("usr") String username,Pageable pageable);
    
    
    public List<DemandeIntervention>findByPanneCategorieNom(@Param("nom") String nom);
    

    public List<DemandeIntervention>  findByUtilisateursAgenceNomAgence(@Param("nom") String nom);
  //  public List<DemandeIntervention> findByUtilisateursMate

    @Query("select  dnr  from  DemandeIntervention dnr,ServiceBMCI snr,Utilisateur unr,Intervention inr where inr.demandeIntervention.id_Demande=dnr.id_Demande and inr.etatIntervention='Non Resolu' and dnr.etat_Demande='Non Resolu' and dnr.utilisateurs.code=unr.code and dnr.service.codeService=snr.codeService and dnr.utilisateurs.username =:usr  and  dnr.visibiliter=true and snr.nom ='Informatique' ")
    public Page<DemandeIntervention>DemandeUsersNonResolu(@Param("usr") String username,Pageable pageable);

    @Query("select  de  from  DemandeIntervention de,ServiceBMCI se,Utilisateur ue where  de.etat_Demande='initiale' and de.utilisateurs.code=ue.code and de.service.codeService=se.codeService and de.utilisateurs.username =:use   and se.nom ='Informatique' ")
    public Page<DemandeIntervention>DemandeUsersEnattente(@Param("use") String username,Pageable pageable);
    @Query("select  dh  from  DemandeIntervention dh,ServiceBMCI sh,Utilisateur uh where  dh.etat_Demande!='initiale' and  dh.etat_Demande!='EnCours' and dh.utilisateurs.code=uh.code and dh.service.codeService=sh.codeService and dh.utilisateurs.username =:use   and sh.nom ='Informatique' ")
    public Page<DemandeIntervention>ConsulterHistorique(@Param("use") String username,Pageable pageable);
    @Query("select  dch  from  DemandeIntervention dch,ServiceBMCI sch,Utilisateur uch ,Panne pch where  dch.utilisateurs.code=uch.code and dch.service.codeService=sch.codeService and dch.panne.idPanne=pch.idPanne  and dch.utilisateurs.username =:us  and dch.panne.etatPanne=:etat and dch.panne.categorie.nom=:pn and  dch.date_Demande=:date1  and sch.nom ='Informatique' ")
    public Page<DemandeIntervention>ChercherDemandeAvecDate(@Param("us")String username, @Param("pn")String panne, @Param("etat")String etatPanne, @Param("date1")Date d1,Pageable pageable);
    @Query("select dm  from  DemandeIntervention dm,ServiceBMCI sn,Utilisateur us where dm.utilisateurs.code=us.code and dm.service.codeService=sn.codeService  and  dm.etat_Demande='Rejeter'")
    public List<DemandeIntervention> DemandeRejeter();
   
    @Query("select  dch  from  DemandeIntervention dch,ServiceBMCI sch,Utilisateur uch ,Panne pch where  dch.utilisateurs.code=uch.code and dch.service.codeService=sch.codeService and dch.panne.idPanne=pch.idPanne  and dch.utilisateurs.username =:us  and dch.panne.etatPanne=:etat and dch.panne.categorie.nom=:pn  and sch.nom ='Informatique' ")
    public Page<DemandeIntervention>ChercherDemandeSansDate(@Param("us")String username, @Param("pn")String panne, @Param("etat")String etatPanne,Pageable pageable);
   


}
