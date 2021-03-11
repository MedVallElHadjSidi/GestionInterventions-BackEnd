package com.example.demo.Services;

import com.example.demo.DAO.*;
import com.example.demo.Entities.*;
import com.example.demo.model.ModelCategorie;
import com.example.demo.model.ModelEditDemande;
import com.example.demo.model.ModelRecherche;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.criteria.CriteriaBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.Deflater;

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
    @Autowired
    private MaterielPanneRepository materielPanneRepository;
    @Autowired 
    private PanneRepository panneRepository;

    @Override
    public int NombreInterventionEncours() {
        return interventionRepository.NmobreServcieEnCours();
    }

    @Override
    public int NombreInterventionResolu() {
        return interventionRepository.NmobreServcieResolu();
    }

    @Override
    public int NombreInterventionNonResolu() {
        return interventionRepository.NmobreServcieNonResolu();
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
        List<Utilisateur>utilisateurs=utilisateurRepository.findAll();
        for (Utilisateur u:utilisateurs){
            if(u.getRoles().contains(r)){
            	if(u.getNbritv()<5) {
        
                    intervenantLibreNames.add(u.getUsername());}

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
                        if (i.getDemandeIntervention().getEtat_Demande().equals("EnCours")&& (i.getDemandeIntervention().getVibinter()))
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
                       if (i.getDemandeIntervention().getEtat_Demande().equals("EnCours")&&(!i.getDemandeIntervention().getVibinter()))
                       demandeInterventions.add(i.getDemandeIntervention());

                   }
               }
           }
        }


        return demandeInterventions;
    }

    @Override
    public Page<DemandeIntervention> DemandeUserResolu(String username,int page) {
        Pageable requestedPage = PageRequest.of(page, 5);
        return demandeRepository.DemandeUsersResolu(username,requestedPage);
    }

    @Override
    public Page<DemandeIntervention> DemandeUserRejeter(String username,int page) {
        Pageable requestedPage = PageRequest.of(page, 5);
        return demandeRepository.DemandeUsersRejeter(username,requestedPage);
    }

    @Override
    public Page<DemandeIntervention> DemandesUserNonResolu(String username, int page) {
        Pageable requestedPage = PageRequest.of(page, 5);

        return demandeRepository.DemandeUsersNonResolu(username,requestedPage);
    }

    @Override
    public Page<DemandeIntervention> DemandeUserEnAttente(String username, int page) {
        Pageable requestedPage = PageRequest.of(page, 5);
        return demandeRepository.DemandeUsersEnattente(username,requestedPage);
    }

    @Override
    public Page<DemandeIntervention> ConsulterHistroriques(String username, int page) {
        Pageable requestedPage = PageRequest.of(page, 5);
        return demandeRepository.ConsulterHistorique(username,requestedPage);
    }

    @Override
    public Page<DemandeIntervention> DemandeUsersRejeterNotifier(String username, int page) {
        Pageable requestedPage = PageRequest.of(page, 5);
        return demandeRepository.DemandeUsersRejeterNotifier(username,requestedPage);
    }

    @Override
    public Page<DemandeIntervention> ChercherComplet(ModelRecherche modelRecherche) {
        Pageable requestedPage = PageRequest.of(modelRecherche.getPage(), 5);
        Page<DemandeIntervention>demandeInterventions=null;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        try {
   
            if(modelRecherche.getDatechercher()!=null) {
                Date d1=formatter.parse(modelRecherche.getDatechercher());
            demandeInterventions= demandeRepository.ChercherDemandeAvecDate(modelRecherche.getUsername(),modelRecherche.getPanne(),modelRecherche.getEtat(),d1,requestedPage);
            if (demandeInterventions!=null){
                for (DemandeIntervention d:demandeInterventions.getContent()){
                    System.out.println(d.getId_Demande());
                }
            }
            }
            else {
            	   demandeInterventions= demandeRepository.ChercherDemandeSansDate(modelRecherche.getUsername(),modelRecherche.getPanne(),modelRecherche.getEtat(),requestedPage);
                   if (demandeInterventions!=null){
                       for (DemandeIntervention d:demandeInterventions.getContent()){
                           System.out.println(d.getId_Demande());
                       }
                   }
            	
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }





    return demandeInterventions;
    }

    @Override
    public Espace EspaceFermerInterventionResolu(Long id) {
        Espace espace=espaceRepository.findById(id).get();
        Role role=rolesRepository.findByRoleName("INTERVENANT");
        espace.setEtatEspace("Fermer");
        
        System.out.println("id interventions"+espace.getIntervention().getIdIntervention());
        Intervention intervention=espace.getIntervention();
        double duree=Approche(CalculerDurre(ConverteDate(intervention.getDemandeIntervention().getDate_Demande()),ConverteDate(new Date())));
        if (intervention!=null){
            System.out.println("Resolu");
            intervention.setEtatIntervention("Reussie");
            intervention.getDemandeIntervention().setEtat_Demande("Resolu");
            intervention.setDureIntervention(duree);
            intervention.setDred(ConvertEnDateTime(intervention.getDureIntervention()));
           // intervention.getDemandeIntervention().setEtat_Demande("Fermer");

           for (Utilisateur u:espace.getUtilisateurs()){
               if (u.getRoles().contains(role)){
            	   u.setNbritv(u.getNbritv()-1);
            		if(u.getNbritv()==0) {
                        u.setEtat("Active");}
                    
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
        double duree=Approche(CalculerDurre(ConverteDate(intervention.getDemandeIntervention().getDate_Demande()),ConverteDate(new Date())));
        
        if (intervention!=null){
            System.out.println("non Resolu");
            intervention.getDemandeIntervention().setEtat_Demande("Non Resolu");
            intervention.setEtatIntervention("Non Resolu");
            intervention.setDureIntervention(duree);
            intervention.setDred(ConvertEnDateTime(intervention.getDureIntervention()));
            
            Role role=rolesRepository.findByRoleName("INTERVENANT");

            for (Utilisateur u:espace.getUtilisateurs()){
                if (u.getRoles().contains(role)){
                	u.setNbritv(u.getNbritv()-1);
                	if(u.getNbritv()==0) {
                    u.setEtat("Active");}
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
        utilisateur1.setNbritv(utilisateur1.getNbritv()+1);
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
        utilisateur1.setNbritv(utilisateur1.getNbritv()+1);
        utilisateur1.setEtat("EnMission");
        
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
      /*  utilisateur1.setEtat("Occuper");*/

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
        demandeIntervention.setVisibiliter(false);
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
        Role role=rolesRepository.findByRoleName("RESPONSABLE");

        List<String>respoNames=new ArrayList<>();

        List<Utilisateur>utilisateurs=utilisateurRepository.UserNamesSansRoles();
        if (utilisateurs!=null){

            for (Utilisateur u:utilisateurs){
                if (u.getRoles().contains(role) && u.getService()==null){
               
                    
                       
                            respoNames.add(u.getUsername());
                        
                   
                    

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
        Role role=rolesRepository.findByRoleName("RESPONSABLE");
        List<Utilisateur> utilisateurs= (List<Utilisateur>) serviceBMCIS.getUtilisateurs();
        for (Utilisateur u:utilisateurs){
            if (u.getRoles().contains(role)){
            for (Role r:u.getRoles()){
                if (r.getRoleName().equals("RESPONSABLE")){
                    rs=true;
                }
            }}


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
        Role role=rolesRepository.findByRoleName("INTERVENANT");
        List<String>intervenantsNames=new ArrayList<>();
        List<Utilisateur>utilisateurs=utilisateurRepository.findAll();
        
        if (utilisateurs!=null){

        	 for (Utilisateur u:utilisateurs){
             	if(u.getRoles().contains(role)&&u.getService()==null) {
             //    System.out.println(u.getUsername());
             		intervenantsNames.add(u.getUsername());
                     
                 
             	}
                 

             }}

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
        Role role=rolesRepository.findByRoleName("S-USER");
        List<Utilisateur>utilisateurs= new ArrayList<>();
        utilisateurs=  (List<Utilisateur>) utilisateurRepository.findAll();
        List<String>usersNames=new ArrayList<>();
   
        for (Utilisateur u:utilisateurs){
        	if(u.getRoles().contains(role)&&u.getAgence()==null) {
        //    System.out.println(u.getUsername());
            
        
                    usersNames.add(u.getUsername());
                
            
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

	@Override
	public DemandeIntervention DemandeRejeterLue(Long id) {
		// TODO Auto-generated method stub
		DemandeIntervention demandeIntervention=demandeRepository.findById(id).get();
		demandeIntervention.setVisibiliter(true);
		return demandeIntervention;
	}

	@Override
	public double ConverteDate(Date d1) {
		// TODO Auto-generated method stub
		double nb=d1.getHours()*3600+d1.getMinutes()*60+d1.getSeconds();
   
		return nb;
		
	}

	@Override
	public double CalculerDurre(double d1, double d2) {
		// TODO Auto-generated method stub
		  double rs=0;
			
		  if(d2>d1) {
		  
		  rs=(d2-d1)/3600;}
		  
		  
		  else { rs=(24)-((d2-d1)/3600); }
		return rs;
	}
	
	@Override
	public double Approche(double r) {
	return 	(double) Math.round(r * 10000) / 10000;
	}
	@Override
	public Date ConvertEnDateTime(double rs) {
		SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
		Date date=new Date();
		Date date2=null;

		double duree=rs*3600;
		int her=(int) (duree/3500);
		date.setHours(her);
		
		double q=duree%3600;
		int minite=(int) (q/60);
		date.setMinutes(minite);
		double seconde=q%60;
		date.setSeconds((int) Approche(seconde));
		String d=format.format(date);
		try {
			date2=format.parse(d);
			System.out.println(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return date2;
		
		
		
	}

	@Override
	public Boolean AddAgenceListUser(String agencename, String[] username) {
		// TODO Auto-generated method stub
		boolean b=false;
	
		
	      Agence agence=agenceRepository.findByNomAgence(agencename);
	      for(String user:username) {
	    	  
	        Utilisateur utilisateur=utilisateurRepository.findByUsername(user);

	        if(agence!=null&&utilisateur!=null){
	        	b=true;
	            utilisateur.setAgence(agence);
	            agence.getUtilisateurs().add(utilisateur);
	            utilisateurRepository.save(utilisateur);

	        }
	      }



	        return b;
	    }

	@Override
	public Boolean AffectServiceLstIntervenant(String nomService, String[] intervenant) {
		// TODO Auto-generated method stub
		boolean b=false;
		   ServiceBMCI serviceBMCI=serviceRepository.findByNom(nomService);
		   for(String user:intervenant) {
			   b=true;
	        Utilisateur utilisateur=utilisateurRepository.findByUsername(user);
	        utilisateur.setService(serviceBMCI);
	        serviceBMCI.getUtilisateurs().add(utilisateur);
		   }
		
		return b;
	}

	@Override
	public DemandeIntervention EditerDemande(ModelEditDemande modelEditDemande) {
		// TODO Auto-generated method stub
		
		    	DemandeIntervention demandeIntervention=demandeRepository.findById(modelEditDemande.getId()).get();
		    	if(demandeIntervention.getVisibiliter()!=true) {
		    	Materiel_Panne materielPanne=materielPanneRepository.findByPanne(demandeIntervention.getPanne());
		    	Materiel materile1=materielRepository.findByNom(modelEditDemande.getMateriel());
		    	materielPanne.setMateriel(materile1);
		    	Panne panne=panneRepository.findById(demandeIntervention.getPanne().getIdPanne()).get();
		    	
		    	demandeIntervention.getPanne().setEtatPanne(modelEditDemande.getEtat());
		    	//demandeIntervention.getPanne().getCategorie().setNom(modelEditDemande.getType());
		    	
		    	demandeIntervention.getPanne().setPhotos(compressBytes(modelEditDemande.getImage().getBytes()));
		        Categorie categorie=categorieRepository.findByNom(modelEditDemande.getType());
		        panne.setCategorie(categorie);
		
		    	
		    
		    	
		    	ServiceBMCI bmci=serviceRepository.findByNom(modelEditDemande.getService());
		    	demandeIntervention.setService(bmci);
		    	demandeIntervention.getPanne().setDescription(modelEditDemande.getDescription());
		    	materielPanne.setPanne(demandeIntervention.getPanne());
		    	
		    	demandeIntervention= demandeRepository.save(demandeIntervention);
		    	
		    	}
		    	else {
		    		
		    		demandeIntervention=null;
		    	}
		    	
		    	
		    	
		    	
		    	return demandeIntervention;
		    }
    public  byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

	@Override
	public DemandeIntervention ChercherByidDemandeInterVensionEncours(Long id) {
		// TODO Auto-generated method stub
		DemandeIntervention demandeIntervention=demandeRepository.findById(id).get();
		demandeIntervention.setVibinter(true);
		//System.out.println(demandeIntervention.getUtilisateurs().isVnd());
		return demandeIntervention;
	}

	@Override
	public List<DemandeIntervention> MissionAsoocierIntervenantResolue(String username) {
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
	                        if (i.getDemandeIntervention().getEtat_Demande().equals("Resolu")&& (i.getDemandeIntervention().getVibinter()))
	                            demandeInterventions.add(i.getDemandeIntervention());

	                    }
	                }
	            }
	        }


	        return demandeInterventions;
	}

	@Override
	public List<DemandeIntervention> MissionAsoocierIntervenantNonResolue(String username) {
		// TODO Auto-generated method stub
		
		
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
                        if (i.getDemandeIntervention().getEtat_Demande().equals("Non Resolu")&&(i.getDemandeIntervention().getVibinter()))
                            demandeInterventions.add(i.getDemandeIntervention());

                    }
                }
            }
        }


        return demandeInterventions;
		
	}

	@Override
	public List<DemandeIntervention> MissionAsoocierIntervenantHisrorique(String username) {
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
	                        if ((i.getDemandeIntervention().getEtat_Demande().equals("Non Resolu")||i.getDemandeIntervention().getEtat_Demande().equals("Resolu") )&&utilisateur.getEtat().equals("EnMission")&& (i.getDemandeIntervention().getVibinter()))
	                            demandeInterventions.add(i.getDemandeIntervention());

	                    }
	                }
	            }
	        }


	        return demandeInterventions;
	}
		
	
    
 
	}

	


