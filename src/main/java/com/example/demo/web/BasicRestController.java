package com.example.demo.web;

import com.example.demo.DAO.*;
import com.example.demo.Entities.*;
import com.example.demo.Services.AccountService;
import com.example.demo.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class BasicRestController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AgenceRepository agenceRepository;
    @Autowired
    private AdesseRepository adesseRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private  ServiceRepository serviceRepository;
    
    @PostMapping("/addMateriel")
    public  Materiel AjouterMateriel(@RequestBody Materiel materiel){
        return  accountService.AddMateriel(materiel);
    }

    @PostMapping("/addUser")
    public Utilisateur AjouterUtilisateur(@RequestBody ModelUser modelUser){
        String username=modelUser.getUsername();
        Utilisateur utilisateur =accountService.findUserByUser(username);
        if (utilisateur!=null)
            throw  new RuntimeException("ce username Exist deja");
        String password=modelUser.getPassword();String confirm=modelUser.getConfirmation();
        if (!password.equals(confirm))
            throw  new RuntimeException("Verifier vOTRE PASSWORD");
        Utilisateur user=new Utilisateur();
        user.setCode(modelUser.getCode());
        user.setNom(modelUser.getNom());
        user.setUsername(modelUser.getUsername());
        user.setPassword(modelUser.getPassword());
        user.setEmail(modelUser.getEmail());

        return  accountService.addUser(user);
    }


    @PostMapping("/addRole")
    public Role AddRole(@RequestBody Role role){


        return  rolesRepository.save(role);
    }

    @PostMapping("/addService")
    public ServiceBMCI AddService(@RequestBody ModelService modelService){
        ServiceBMCI serviceBMCI=new ServiceBMCI();
        serviceBMCI.setCodeService(modelService.getCodeService());
        serviceBMCI.setNom(modelService.getNomService());


        return   serviceRepository.save(serviceBMCI);
    }
    @PostMapping("/AffectRespoService")
    public ServiceBMCI AffectRespoService(@RequestBody ModelIntervenantService service){
        System.out.println(service);

        return   accountService.AffectRespoServic(service.getServiceName(),service.getIntervenantName());

    }

    @PostMapping("/affecterRole")
    public Utilisateur AffecterRole(@RequestBody ModelAffectationRole role){


      return   accountService.AddRoles(role.getUsername(),role.getRolename());
    }
    @PostMapping("/addServiceIntervenant")
    public ServiceBMCI AddIntSer(@RequestBody ModelIntervenantService modelIntervenantService)
    {
        return accountService.AffectServiceIntervenant(modelIntervenantService.getServiceName(),modelIntervenantService.getIntervenantName());
    }


    @PostMapping("/ajouterCategorie")
    public Categorie ajouterCategorie(@RequestBody ModelCategorie categorie){
        System.out.println(categorie);
        ModelCategorie modelCategorie=new ModelCategorie(categorie.getNom(),categorie.getParent());

        return accountService.AddCategorie(modelCategorie);

    }

    @PostMapping("/addAdresse")
    public Adresse AddAdresse(@RequestBody Adresse adresse){
        return accountService.AddAdresse(adresse);
    }

    @PostMapping("/affectAgUser")

    public  Utilisateur AffectAgUser(@RequestBody ModelAffectAgUser modelAffectAgUser){

        return accountService.AddAgenceUser(modelAffectAgUser.getNomAgence(),modelAffectAgUser.getUsername());
    }



    @PostMapping("/addAgence")
    public ResponseEntity<Agence> AddAgence(@RequestBody ModelAgence modelAgence) throws  ResourceNotFoundException{

        System.out.println("nomAgence "+modelAgence.toString());
        String codeAdresse=modelAgence.getCodeAdresse();
        Agence agence1=new Agence();

        Adresse adresse =adesseRepository.findById(codeAdresse).orElseThrow(()->   new RuntimeException("cette adresse n'Exist pas"));
        if(adresse!=null){
            agence1.setNomAgence(modelAgence.getNomAgence());
            agence1.setAdresse(adresse);
            return ResponseEntity.ok().body(agenceRepository.save(agence1));
        }
       return ResponseEntity.ok().body(agence1);
    }
    @GetMapping("/chercheByCodeAdresse/{code}")
    public ResponseEntity<Adresse> chercherAdresseByCode(@PathVariable String code)throws  ResourceNotFoundException {

        System.out.println(code);
            Adresse adresse = adesseRepository.findById(code)
                    .orElseThrow(()->new ResourceNotFoundException("Employee not found for this id :: "));

            System.out.println("adresse chercher"+adresse.toString());
            return ResponseEntity.ok().body(adresse);

        }
    @GetMapping("/ChercherByCodeAdresseAll")
    public List<String>ChercherByCodeAdresseAll(){
        return  adesseRepository.CodeAdresses();
    }
    @GetMapping("/ChercherAllRolesNames")
    public List<String>ChercherAllRolesNames(){
        return  rolesRepository.RolesName();
    }

    @GetMapping("/ChercherUserSansRole")
    public List<String>ChercherUserSansRole(){
        return  accountService.UtilisateurSansRoles();
    }
    @GetMapping("/AgencesNames")
    public List<String>AgencesNames(){
        return  agenceRepository.AgenceNmes();
    }

    @GetMapping("/UserNames")
    public List<String>UserNames(){
        return  accountService.UtilisateursNames();
    }
    @GetMapping("/AffectationIntervenant")
    public List<String>IntervenantAddService(){
        return accountService.IntervenantNames();

    }

    @GetMapping("/ServicesNames")
    public List<String>ServicesNames(){
        return  serviceRepository.ServiceNames();
    }
    @GetMapping("/RespoSansService")
    public List<String>RespoSansService(){
        return accountService.RespoSansService();
    }



    @GetMapping("/ServicesSansRespo")
    public List<String>ServicesNamesSansRespo(){
        return accountService.ServiceSansRespo();
    }











}
