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

    @Query("select dc  from  DemandeIntervention dc,ServiceBMCI sin where dc.service.codeService=sin.codeService and  dc.etat_Demande='EnCours' and  dc.visibiliter=true and sin.nom ='Informatique' ")
    public List<DemandeIntervention> DemandeEnCours();

    @Query("select di  from  DemandeIntervention di,ServiceBMCI si,Utilisateur ut where di.utilisateurs.code=ut.code and di.service.codeService=si.codeService and di.utilisateurs.username =:us and  di.etat_Demande='EnCours' and  di.visibiliter=true and si.nom ='Informatique' ")
    public List<DemandeIntervention>DemandeUsersEnCours(@Param("us") String username);

    @Query("select dm  from  DemandeIntervention dm,ServiceBMCI sn,Utilisateur us where dm.utilisateurs.code=us.code and dm.service.codeService=sn.codeService and dm.utilisateurs.username =:un and  dm.etat_Demande='Rejeter' and  dm.visibiliter=true and sn.nom ='Informatique' ")
    public List<DemandeIntervention> DemandeUsersRejeter(@Param("un") String username);

    @Query("select  d  from  DemandeIntervention d,ServiceBMCI s,Utilisateur u,Intervention i where i.demandeIntervention.id_Demande=d.id_Demande and i.EtatIntervention='Resolu'  and d.utilisateurs.code=u.code and d.service.codeService=s.codeService and d.utilisateurs.username =:usr  and  d.visibiliter=true and s.nom ='Informatique' ")
    public List<DemandeIntervention>DemandeUsersResolu(@Param("usr") String username);



}
