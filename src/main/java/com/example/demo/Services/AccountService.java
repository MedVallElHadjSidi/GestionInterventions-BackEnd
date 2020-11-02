package com.example.demo.Services;


import com.example.demo.Entities.*;
import com.example.demo.model.ModelCategorie;

import java.util.List;

public interface AccountService {
    public List<DemandeIntervention>DemandeUserResolu(String username);
    public  List<DemandeIntervention>DemandeUserRejeter(String username);
    public  Espace EspaceFermerInterventionResolu(Long id);
    public  Espace EspaceFermerIntervention(Long id);
    public  List<DemandeIntervention>DEMANDE_User_EnCours(String username);
    public  Long CreerEspaceInter1(Long id,String username);
    public void DemandeRejeter(DemandeIntervention demandeIntervention);
    public List<DemandeIntervention> NouveauxDemandes();
    public Utilisateur ServiceRespo(String servicename);
    public  List<String>MaterielNames(String username);
    public List<String>   RespoSansService();
    public  List<String>ServiceSansRespo();
    public ServiceBMCI AffectServiceIntervenant(String nomService, String intervenant);
    public  ServiceBMCI AffectRespoServic(String nomService,String respo);
    public  List<String>IntervenantNames();
    public Materiel AddMateriel(Materiel materiel);
    public Categorie AddCategorie(ModelCategorie categorie);
    public List<String>UtilisateurSansRoles();
    public  List<String>UtilisateursNames();
    public Utilisateur addUser(Utilisateur user);
    public Role addRole(Role roles);
    public Utilisateur findUserByUser(String codeUser);
    public  Utilisateur  AddRoles(String username,String rolename);
    public  Utilisateur AddAgenceUser(String agencename,String username);
    public void AddIntervenant(String nomService,String username);
    public Adresse AddAdresse(Adresse adresse);
    public Agence AddAgence(String nomAgence ,String adresse);
    public  void AddService(String codeService,String nomService,String username);
    public Adresse findByCodeAdresse(String codeAdresse);



}
