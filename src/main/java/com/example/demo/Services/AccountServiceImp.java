package com.example.demo.Services;

import com.example.demo.DAO.*;
import com.example.demo.Entities.*;
import com.example.demo.model.ModelCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional

public class AccountServiceImp implements AccountService{
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    private AdesseRepository adesseRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private  CategorieRepository categorieRepository;
    @Autowired
    private  MaterielRepository materielRepository;
    @Autowired
    private  DemandeRepository demandeRepository;
    @Autowired
    private  EspaceRepository espaceRepository;
    @Autowired
    private  InterventionRepository interventionRepository;

    @Override
    public int NombreInterventionEncours() {
        return 4;
    }

    @Override
    public int NombreInterventionResolu() {
        return 40;
    }

    @Override
    public int NombreInterventionNonResolu() {
        return 20;
    }

    @Override
    public Utilisateur UtilisateurEnMission(String username) {

        Role r=rolesRepository.findByRoleName("INTERVENANT");
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username);
        if(utilisateur.getRoles().contains(r)){
            utilisateur.setEtat("EnMission");
        }


        return utilisateur;
    }

    @Override
    public List<String> IntervenantServiceLibre() {
        List<String>intervenantLibreNames=new ArrayList<>();
        Role r=rolesRepository.findByRoleName("INTERVENANT");
        List<Utilisateur>utilisateurs=utilisateurRepository.IntervenantNameAvecService();
        for (Utilisateur u:utilisateurs){
            if(u.getRoles().contains(r)){

                    intervenantLibreNames.add(u.getUsername());

            }
        }
        System.out.println("les intervevabts libres:"+intervenantLibreNames.size());
        return intervenantLibreNames;
    }

    @Override
    public List<DemandeIntervention> DemandeAsoocierIntervenantEnCours(String username) {
        System.out.println("intervenant :"+username);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username);
        List<DemandeIntervention>demandeInterventions=new ArrayList<>();
        List<Intervention>interventions=interventionRepository.findAll();
        System.out.println("interventions Associer a tous"+interventions.size());
        Role role=rolesRepository.findByRoleName("INTERVENANT");

        if(utilisateur!=null && interventions.size()>0){
            if(utilisateur.getRoles().contains(role)==true){
                System.out.println("resultat"+utilisateur.getRoles().contains(role));
                for (Intervention i:interventions){
                    if (i.getEspace().getUtilisateurs().contains(utilisateur)==true){
                        if (i.getDemandeIntervention().getEtat_Demande().equals("EnCours")&&utilisateur.getEtat().equals("EnMission"))
                            demandeInterventions.add(i.getDemandeIntervention());

                    }
                }
            }
        }


        return demandeInterventions;
    }

    @Override
    public List<DemandeIntervention> DemandeAsoocierIntervenant(String username) {
        System.out.println("intervenant :"+username);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username);
        List<DemandeIntervention>demandeInterventions=new ArrayList<>();
        List<Intervention>interventions=interventionRepository.findAll();
        System.out.println("interventions Associer a tous"+interventions.size());
        Role role=rolesRepository.findByRoleName("INTERVENANT");

        if(utilisateur!=null && interventions.size()>0){
           if(utilisateur.getRoles().contains(role)==true){
               System.out.println("resultat"+utilisateur.getRoles().contains(role));
               for (Intervention i:interventions){
                   if (i.getEspace().getUtilisateurs().contains(utilisateur)==true){
                       if (i.getDemandeIntervention().getEtat_Demande().equals("EnCours")&&utilisateur.getEtat().equals("Occuper"))
                       demandeInterventions.add(i.getDemandeIntervention());

                   }
               }
           }
        }


        return demandeInterventions;
    }

    @Override
    public List<DemandeIntervention> DemandeUserResolu(String username) {
        return demandeRepository.DemandeUsersResolu(username);
    }

    @Override
    public List<DemandeIntervention> DemandeUserRejeter(String username) {
        return demandeRepository.DemandeUsersRejeter(username);
    }

    @Override
    public Espace EspaceFermerInterventionResolu(Long id) {
        Espace espace=espaceRepository.findById(id).get();
        Role role=rolesRepository.findByRoleName("INTERVENANT");
        espace.setEtatEspace("Fermer");
        System.out.println("id interventions"+espace.getIntervention().getIdIntervention());
        Intervention intervention=espace.getIntervention();
        if (intervention!=null){
            System.out.println("Resolu");
            intervention.setEtatIntervention("Resolu");
            intervention.getDemandeIntervention().setEtat_Demande("Fermer");

           for (Utilisateur u:espace.getUtilisateurs()){
               if (u.getRoles().contains(role)){
                   u.setEtat("Active");
               }
            }

        }
        return  espace;
    }

    @Override
    public Espace EspaceFermerIntervention(Long id) {
        Espace espace=espaceRepository.findById(id).get();
        espace.setEtatEspace("Fermer");
        System.out.println("id interventions"+espace.getIntervention().getIdIntervention());
        Intervention intervention=espace.getIntervention();
        if (intervention!=null){
            System.out.println("non Resolu");
            intervention.setEtatIntervention("Non Resolu");
            Role role=rolesRepository.findByRoleName("INTERVENANT");

            for (Utilisateur u:espace.getUtilisateurs()){
                if (u.getRoles().contains(role)){
                    u.setEtat("Active");
                }
            }
        }
        return  espace;


    }

    @Override
    public Page<DemandeIntervention> DEMANDE_User_EnCours(int page, String username) {

        Pageable requestedPage = PageRequest.of(page, 5);


        return demandeRepository.DemandeUsersEnCours(username, requestedPage);
    }


    @Override
    public Long CreerEspaceInter1(Long id, String username) {
        Espace res=new Espace();
        List<Utilisateur>utilisateurs=new ArrayList<>();
        DemandeIntervention demandeIntervention=demandeRepository.findById(id).get();
        demandeIntervention.setEtat_Demande("EnCours");
        demandeIntervention.setVisibiliter(true);
        Utilisateur utilisateur1=utilisateurRepository.findByUsername(username);
        Utilisateur utilisateur2=demandeIntervention.getUtilisateurs();
        utilisateurs.add(utilisateur1);
        utilisateurs.add(utilisateur2);
        Intervention intervention=new Intervention();
        intervention.setDemandeIntervention(demandeIntervention);
        Intervention intervention1=interventionRepository.save(intervention);
        demandeIntervention.getInterventions().add(intervention1);
        demandeRepository.save(demandeIntervention);

        Espace espace=new Espace();

        if (intervention1!=null){
            espace.setIntervention(intervention1);
            espace.setUtilisateurs(utilisateurs);
            res=espaceRepository.save(espace);
            intervention1.setEspace(res);
            demandeIntervention.getInterventions().add(intervention1);
        }

        System.out.println(res.getIntervention().getIdIntervention()+"intervention"+res.getIdEspace());

        return res.getIdEspace();
    }

    @Override
    public Long CreerEspaceInter2(Long id, String intervenant, String respo) {


        Espace res=new Espace();
        List<Utilisateur>utilisateurs=new ArrayList<>();
        DemandeIntervention demandeIntervention=demandeRepository.findById(id).get();
        demandeIntervention.setEtat_Demande("EnCours");
        demandeIntervention.setVisibiliter(true);
        Utilisateur utilisateur1=utilisateurRepository.findByUsername(intervenant);
        Utilisateur utilisateur2=demandeIntervention.getUtilisateurs();
        Utilisateur utilisateur3=utilisateurRepository.findByUsername(respo);

        utilisateurs.add(utilisateur1);
        utilisateurs.add(utilisateur2);
        utilisateurs.add(utilisateur3);

        Intervention intervention=new Intervention();
        intervention.setDemandeIntervention(demandeIntervention);
        Intervention intervention1=interventionRepository.save(intervention);
        demandeIntervention.getInterventions().add(intervention1);
        demandeRepository.save(demandeIntervention);
        utilisateur1.setEtat("Occuper");

        Espace espace=new Espace();

        if (intervention1!=null){
            espace.setIntervention(intervention1);
            espace.setUtilisateurs(utilisateurs);
            System.out.println(espace.getUtilisateurs().size());
            res=espaceRepository.save(espace);
            intervention1.setEspace(res);
            demandeIntervention.getInterventions().add(intervention1);
        }
        System.out.println(res.getIntervention().getIdIntervention()+"intervention"+res.getIdEspace());

        return intervention1.getIdIntervention();
    }

    @Override
    public void DemandeRejeter(DemandeIntervention demandeIntervention) {
        demandeIntervention.setEtat_Demande("Rejeter");
        demandeRepository.save(demandeIntervention);

    }

    @Override
    public List<DemandeIntervention> NouveauxDemandes() {
        return demandeRepository.NouveauDemande();
    }

    @Override
    public Utilisateur ServiceRespo(String servicename) {
        Utilisateur utilisateur=new Utilisateur();
        List<Utilisateur>utilisateurs=serviceRepository.UTILISATEURSServices(servicename);
        for (Utilisateur u :utilisateurs){
            for (Role r:u.getRoles()){
                if (r.getRoleName().equals("RESPONSABLE")){
                    utilisateur=u;
                }
            }

        }
        return utilisateur;
    }

    @Override
    public List<String> MaterielNames(String username) {

        return materielRepository.MaterielsNames(username);
    }

    @Override
    public List<String> RespoSansService() {
        System.out.println(" respo sans service");

        List<String>respoNames=new ArrayList<>();

        List<Utilisateur>utilisateurs=utilisateurRepository.UserNamesSansRoles();
        if (utilisateurs!=null){

            for (Utilisateur u:utilisateurs){
                if (!u.getRoles().isEmpty()){
                    for (Role r:u.getRoles()){
                        if (r.getRoleName().equals("RESPONSABLE")&&u.getService()==null){
                            respoNames.add(u.getUsername());
                        }
                    }

                }
            }
        }
        return respoNames;
    }

    @Override
    public List<String> ServiceSansRespo() {
        boolean b=false;
        System.out.println("service sans respo");
        List<ServiceBMCI>serviceBMCIS=serviceRepository.ServiceNamesSansAdmi();
        List<String>servicesNames=new ArrayList<>();
        for (ServiceBMCI s:serviceBMCIS){
            if(s.getUtilisateurs().isEmpty()){
                servicesNames.add(s.getNom());
            }
            else{
               boolean rf=ServiceSnsRS(s);
               if (rf==false){

                   servicesNames.add(s.getNom());
               }




            }

            }

        return servicesNames;
    }
    public boolean ServiceSnsRS(ServiceBMCI serviceBMCIS){
        boolean rs=false;
        List<Utilisateur> utilisateurs= (List<Utilisateur>) serviceBMCIS.getUtilisateurs();
        for (Utilisateur u:utilisateurs){
            for (Role r:u.getRoles()){
                if (r.getRoleName().equals("RESPONSABLE")){
                    rs=true;
                }
            }


        }



    return  rs;
    }



    @Override
    public ServiceBMCI AffectServiceIntervenant(String nomService, String intervenant) {

        ServiceBMCI serviceBMCI=serviceRepository.findByNom(nomService);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(intervenant);
        utilisateur.setService(serviceBMCI);
        return serviceBMCI;
    }

    @Override
    public ServiceBMCI AffectRespoServic(String nomService, String respo) {
        ServiceBMCI serviceBMCI=serviceRepository.findByNom(nomService);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(respo);
        utilisateur.setService(serviceBMCI);
        return serviceBMCI;
    }

    @Override
    public List<String> IntervenantNames() {
        List<String>intervenantsNames=new ArrayList<>();
        List<Utilisateur>utilisateurs=utilisateurRepository.UserNamesSansRoles();
        if (utilisateurs!=null){

            for (Utilisateur u:utilisateurs){
                if (!u.getRoles().isEmpty()){
                    for (Role r:u.getRoles()){
                        if (r.getRoleName().equals("INTERVENANT")&&u.getService()==null){
                            intervenantsNames.add(u.getUsername());
                        }
                    }

                }
            }
        }

        return intervenantsNames;
    }

    @Override
    public Materiel AddMateriel(Materiel materiel) {
        return materielRepository.save(materiel);
    }

    @Override
    public Categorie AddCategorie( ModelCategorie categorie) {
        Categorie categorie1=categorieRepository.CategorieName(categorie.getParent());
        Categorie categorie2=new Categorie();
        categorie2.setNom(categorie.getNom());
        categorie2.setParent(categorie1);

        return categorieRepository.save(categorie2);
    }

    @Override
    public List<String> UtilisateurSansRoles() {
        List<Utilisateur>utilisateurs= new ArrayList<>();
          utilisateurs=      (List<Utilisateur>) utilisateurRepository.UserNamesSansRoles();
        List<String>usersSansRole=new ArrayList<>();
        for (Utilisateur u:utilisateurs){

            if (u.getRoles().isEmpty()){
                usersSansRole.add(u.getUsername());
                System.out.println("userSans Roles"+u.getUsername());
            }
        }
        return usersSansRole;
    }

    @Override
    public List<String> UtilisateursNames() {
        List<Utilisateur>utilisateurs= new ArrayList<>();
        utilisateurs=      (List<Utilisateur>) utilisateurRepository.UserNamesSansRoles();
        List<String>usersNames=new ArrayList<>();
        for (Utilisateur u:utilisateurs){
            for (Role r:u.getRoles()){
                if (r.getRoleName().equals("S-USER")&&u.getAgence()==null){
                    usersNames.add(u.getUsername());
                }
            }

        }



        return usersNames;
    }

    @Override
    public Utilisateur addUser(Utilisateur user) {
        String passwdBrpt=bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(passwdBrpt);
        return utilisateurRepository.save(user);
    }

    @Override
    public Role addRole(Role roles) {
        return rolesRepository.save(roles);
    }

    @Override
    public Utilisateur findUserByUser(String codeUser) {
        return utilisateurRepository.findByUsername(codeUser);
    }

    @Override
    public Utilisateur AddRoles(String username, String rolename) {
       Role role=rolesRepository.findByRoleName(rolename);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username);
        utilisateur.getRoles().add(role);

        return utilisateur;





    }

    @Override
    public Utilisateur AddAgenceUser(String agencename, String username) {
        Agence agence=agenceRepository.findByNomAgence(agencename);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username);

        if(agence!=null&&utilisateur!=null){
            utilisateur.setAgence(agence);
            utilisateurRepository.save(utilisateur);

        }



        return utilisateur;
    }

    @Override
    public void AddIntervenant(String codeService, String username) {
        Utilisateur utilisateur =utilisateurRepository.findByUsername(username);
        ServiceBMCI service=serviceRepository.findById(codeService).get();


        if (utilisateur!=null){
            List<Role>roles= (List<Role>) utilisateur.getRoles();
            for (Role role:roles){
                if (role.getRoleName().equals("INTERVENANT"))
                {
                    service.getUtilisateurs().add(utilisateur);

                }
            }
        }

    }
    @Override
    public Adresse AddAdresse(Adresse adresse) {
        return  adesseRepository.save(adresse);

    }

    @Override
    public Agence AddAgence(String nomAgence, String adresse) {
        Agence agence=new Agence();
        Adresse adresseres=adesseRepository.findById(adresse).get();
        System.out.println(adresse.toString());
        if (adresseres!=null){

            agence=agenceRepository.save(new Agence(null,nomAgence,adresseres,null,null));
        }
        return agence;
    }

    @Override
    public void AddService(String codeservice,String nomService, String username) {
        Utilisateur utilisateur =utilisateurRepository.findByUsername( username);
        if (utilisateur!=null){
            List<Role>roles= (List<Role>) utilisateur.getRoles();
            for (Role role:roles){
                if (role.getRoleName().equals("RESPONSABLE"))
                {
                    List<Utilisateur>utilisateurs=new ArrayList<>();
                    utilisateurs.add(utilisateur);
                serviceRepository.save(new ServiceBMCI(codeservice,nomService,utilisateurs));

                }
            }
        }
    }

    @Override
    public Adresse findByCodeAdresse(String codeAdresse) {

        return adesseRepository.findById(codeAdresse).get();
    }


}
