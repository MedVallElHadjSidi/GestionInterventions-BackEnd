package com.example.demo.web;

import com.example.demo.DAO.*;
import com.example.demo.Entities.*;
import com.example.demo.Services.AccountService;
import com.example.demo.model.*;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

@RestController
@CrossOrigin(origins = "*")


public class BasicRestController {
    @Autowired
    private DemandeRepository demandeRepository;
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
    private ServiceRepository serviceRepository;
    @Autowired
    private MaterielRepository materielRepository;

    @PostMapping("/addMateriel")
    public Materiel AjouterMateriel(@RequestBody ModelMateriel materiel) {
        Agence agence = agenceRepository.findByNomAgence(materiel.getAgence());
        Materiel materiel1 = new Materiel();
        materiel1.setNom(materiel.getNom());
        materiel1.setProcesseur(materiel.getProcesseur());
        materiel1.setModel(materiel.getModel());
        materiel1.setAgence(agence);

        return accountService.AddMateriel(materiel1);
    }

    @PostMapping("/addUser")
    public Utilisateur AjouterUtilisateur(@RequestBody ModelUser modelUser) {
        String username = modelUser.getUsername();
        Utilisateur utilisateur = accountService.findUserByUser(username);
        if (utilisateur != null)
            throw new RuntimeException("ce username Exist deja");
        String password = modelUser.getPassword();
        String confirm = modelUser.getConfirmation();
        if (!password.equals(confirm))
            throw new RuntimeException("Verifier vOTRE PASSWORD");
        Utilisateur user = new Utilisateur();
        user.setCode(modelUser.getCode());
        user.setNom(modelUser.getNom());
        user.setUsername(modelUser.getUsername());
        user.setPassword(modelUser.getPassword());
        user.setEmail(modelUser.getEmail());

        return accountService.addUser(user);
    }


    @PostMapping("/addRole")
    public Role AddRole(@RequestBody Role role) {


        return rolesRepository.save(role);
    }

    @PostMapping("/addService")
    public ServiceBMCI AddService(@RequestBody ModelService modelService) {
        ServiceBMCI serviceBMCI = new ServiceBMCI();
        serviceBMCI.setCodeService(modelService.getCodeService());
        serviceBMCI.setNom(modelService.getNomService());


        return serviceRepository.save(serviceBMCI);
    }

    @PostMapping("/AffectRespoService")
    public ServiceBMCI AffectRespoService(@RequestBody ModelIntervenantService service) {
        System.out.println(service);

        return accountService.AffectRespoServic(service.getServiceName(), service.getIntervenantName());

    }

    @PostMapping("/affecterRole")
    public Utilisateur AffecterRole(@RequestBody ModelAffectationRole role) {


        return accountService.AddRoles(role.getUsername(), role.getRolename());
    }

    @PostMapping("/addServiceIntervenant")
    public ServiceBMCI AddIntSer(@RequestBody ModelIntervenantService modelIntervenantService) {
        return accountService.AffectServiceIntervenant(modelIntervenantService.getServiceName(), modelIntervenantService.getIntervenantName());
    }


    @PostMapping("/ajouterCategorie")
    public Categorie ajouterCategorie(@RequestBody ModelCategorie categorie) {
        System.out.println(categorie);
        ModelCategorie modelCategorie = new ModelCategorie(categorie.getNom(), categorie.getParent());

        return accountService.AddCategorie(modelCategorie);

    }

    @PostMapping("/addAdresse")
    public Adresse AddAdresse(@RequestBody Adresse adresse) {
        return accountService.AddAdresse(adresse);
    }

    @PostMapping("/affectAgUser")

    public Utilisateur AffectAgUser(@RequestBody ModelAffectAgUser modelAffectAgUser) {

        return accountService.AddAgenceUser(modelAffectAgUser.getNomAgence(), modelAffectAgUser.getUsername());
    }


    @PostMapping("/addAgence")
    public ResponseEntity<Agence> AddAgence(@RequestBody ModelAgence modelAgence) throws ResourceNotFoundException {

        System.out.println("nomAgence " + modelAgence.toString());
        String codeAdresse = modelAgence.getCodeAdresse();
        Agence agence1 = new Agence();

        Adresse adresse = adesseRepository.findById(codeAdresse).orElseThrow(() -> new RuntimeException("cette adresse n'Exist pas"));
        if (adresse != null) {
            agence1.setNomAgence(modelAgence.getNomAgence());
            agence1.setAdresse(adresse);
            return ResponseEntity.ok().body(agenceRepository.save(agence1));
        }
        return ResponseEntity.ok().body(agence1);
    }

    @GetMapping("/chercheByCodeAdresse/{code}")
    public ResponseEntity<Adresse> chercherAdresseByCode(@PathVariable String code) throws ResourceNotFoundException {

        System.out.println(code);
        Adresse adresse = adesseRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: "));

        System.out.println("adresse chercher" + adresse.toString());
        return ResponseEntity.ok().body(adresse);

    }

    @GetMapping("/ChercherAllMaterielNames/{username}")
    public List<String> ChercherAllMaterielNames(@PathVariable String username) {
        return materielRepository.MaterielsNames(username);
    }

    @GetMapping("/ChercherAllCategorieParent")
    public List<String> ChercherAllCategorieParent() {
        return categorieRepository.CategorieNamesParent();
    }


    @GetMapping("/ChercherSousCategorieNames/{nom}")
    public List<String> ChercherSousCategorieNames(@PathVariable String nom) {
        return categorieRepository.SousCategorieName(nom);
    }

    @GetMapping("/ChercherByCodeAdresseAll")
    public List<String> ChercherByCodeAdresseAll() {
        return adesseRepository.CodeAdresses();
    }

    @GetMapping("/ChercherAllRolesNames")
    public List<String> ChercherAllRolesNames() {
        return rolesRepository.RolesName();
    }

    @GetMapping("/ChercherUserSansRole")
    public List<String> ChercherUserSansRole() {
        return accountService.UtilisateurSansRoles();
    }

    @GetMapping("/AgencesNames")
    public List<String> AgencesNames() {
        return agenceRepository.AgenceNmes();
    }

    @GetMapping("/UserNames")
    public List<String> UserNames() {
        return accountService.UtilisateursNames();
    }

    @GetMapping("/AffectationIntervenant")
    public List<String> IntervenantAddService() {
        return accountService.IntervenantNames();

    }

    @GetMapping("/ServicesNames")
    public List<String> ServicesNames() {
        return serviceRepository.ServiceNames();
    }

    @GetMapping("/RespoSansService")
    public List<String> RespoSansService() {
        return accountService.RespoSansService();
    }


    @GetMapping("/ServicesSansRespo")
    public List<String> ServicesNamesSansRespo() {
        return accountService.ServiceSansRespo();
    }

    @GetMapping("/Notification")
    public int Notification() {
        return demandeRepository.NombreDeNouveauMessage();
    }

    @GetMapping("/NouveauxDemandes")
    public List<DemandeIntervention> NouveauDemandes()   {
        List<DemandeIntervention>demandeInterventions=accountService.NouveauxDemandes();
        for(DemandeIntervention d:demandeInterventions){
            System.out.println(d.getId_Demande());
        }


               return demandeInterventions ;
    }



    @GetMapping("/ChercherByidDemande/{id}")
    public DemandeIntervention ChercherByIdDemande(@PathVariable Long id) throws SQLException {
        DemandeIntervention demandeIntervention=demandeRepository.findById(id).get();
        demandeIntervention.getPanne().setPhotos(decompressBytes(demandeIntervention.getPanne().getPhotos()));
        return  demandeIntervention;
    }


    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        }catch (DataFormatException e) {
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }
    @GetMapping("/rejeter/{d}")
    public void DemandeRejeter(@PathVariable Long d){
        DemandeIntervention demandeIntervention=demandeRepository.findById(d).get();
        demandeIntervention.setEtat_Demande("Rejeter");
        demandeIntervention.setVisibiliter(true);
        accountService.DemandeRejeter(demandeIntervention);
    }





}












