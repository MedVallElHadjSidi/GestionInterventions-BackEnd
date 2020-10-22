package com.example.demo.Services;

import com.example.demo.DAO.*;
import com.example.demo.Entities.*;
import com.example.demo.model.ModelCategorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Service

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
        Espace espace=new Espace();

        if (intervention1!=null){
            espace.setIntervention(intervention1);
            espace.setUtilisateurs(utilisateurs);
            res=espaceRepository.save(espace);
            intervention.setEspace(res);



        }



        System.out.println(res.getIntervention().getIdIntervention()+"intervention"+res.getIdEspace());

        return res.getIdEspace();
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
