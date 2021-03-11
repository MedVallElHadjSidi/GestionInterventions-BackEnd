package com.example.demo.webSocket;


import com.example.demo.DAO.*;
import com.example.demo.Entities.*;
import com.example.demo.Services.AccountService;
import com.example.demo.model.EnvoyerMessage;
import com.example.demo.model.ModelDemande;
import com.example.demo.model.ModelEditDemande;
import com.example.demo.model.ModelMessage;
import com.example.demo.model.NotificationIntervenant;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;


import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import java.io.*;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import java.util.zip.Deflater;

@RestController
@RequestMapping(value = "/app")
@CrossOrigin(origins = "*")
@Transactional
public class WebSocketResetController {
    int count=0;

    @Autowired
    private  EspaceRepository espaceRepository;
    @Autowired
    private  InterventionRepository interventionRepository;

    @Autowired
    private MaterielRepository materielRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private DemandeRepository demandeRepository;
    @Autowired
    private PanneRepository panneRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private  UtilisateurRepository utilisateurRepository;
    @Autowired
    private MaterielPanneRepository materielPanneRepository;


    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketResetController(SimpMessagingTemplate template) {
        this.template = template;
    }
    @PostMapping  (value = "/envoyer")
    @ResponseBody
    public DemandeIntervention sendReset(@RequestBody  ModelDemande message) throws IOException, SQLException {


        int count = 0;


        int i = 0;
        Long envoyer = null;
        DemandeIntervention demandeInterventionsNonVue = new DemandeIntervention();

        System.out.println(message.getImage().length());
        /*
        byte[] bdata = message.getImage().getBytes(1, (int)message.getImage().length());
        String data1 = new String(bdata);
        */
        //System.out.println(file.getOriginalFilename());
      /*  byte[]bdata= message.getImage().getBytes();
        System.out.println(bdata.length);
        Blob b = new SerialBlob(bdata);*/
        if (message.getImage().getBytes().length > 1048576) {
            System.out.print("gros image");
            return null;

        } else {


            DemandeIntervention demandeIntervention = new DemandeIntervention();

            String userDemande = message.getUserDemander();
            System.out.println(userDemande);
            String typePanne = message.getType();
            String materiel = message.getMateriel();
            System.out.println(materiel);
            String service = message.getService();

        /* byte[] encodeBase64Byte = Base64.encodeBase64(message.getImage().getBytes());
        Blob b = new SerialBlob(encodeBase64Byte);
        */

            Panne panne = new Panne();
            ServiceBMCI serviceBMCI = serviceRepository.findByNom(service);
            Materiel materiel1 = materielRepository.findByNom(materiel);
            Utilisateur utilisateur = utilisateurRepository.findByUsername(userDemande);
            Categorie categorie = categorieRepository.findByNom(typePanne);
            panne.setCategorie(categorie);
            panne.setEtatPanne(message.getEtat());
            panne.setDescription(message.getDescription());
            panne.setPhotos(compressBytes(message.getImage().getBytes()));


            Panne panne1 = panneRepository.save(panne);
            Materiel_Panne materiel_Panne=new Materiel_Panne();

            materiel_Panne.setMateriel(materiel1);
            materiel_Panne.setPanne(panne1);
            
            materielPanneRepository.save(materiel_Panne);


            if (message != null) {


                demandeIntervention.setPanne(panne1);
                demandeIntervention.setVisibiliter(false);
                demandeIntervention.setService(serviceBMCI);
                demandeIntervention.setUtilisateurs(utilisateur);
                Utilisateur utilisateur1 = accountService.ServiceRespo(message.getService());
                String username = utilisateur1.getUsername();
                demandeInterventionsNonVue = demandeRepository.save(demandeIntervention);

                // count=demandeRepository.NombreDeNouveauMessage();
                envoyer = demandeInterventionsNonVue.getId_Demande();
                System.out.println(envoyer);


                this.template.convertAndSend("/topic/replay" + "/" + username, demandeInterventionsNonVue);

            }


            return demandeInterventionsNonVue;
            // System.out.println(message.getImage().length());
        /*
        byte[] bdata = message.getImage().getBytes(1, (int)message.getImage().length());
        String data1 = new String(bdata);
        */
            //System.out.println(file.getOriginalFilename());
      /*  byte[]bdata= message.getImage().getBytes();
        System.out.println(bdata.length);
        Blob b = new SerialBlob(bdata);*/

/*
        DemandeIntervention demandeIntervention=new DemandeIntervention();

        String userDemande=message.getUserDemander();
        System.out.println(userDemande);
        String typePanne =message.getType();
        String materiel=message.getMateriel();
        System.out.println(materiel);
        String service=message.getService();

        byte[] encodeBase64Byte = Base64.encodeBase64(message.getImage().getBytes());
        Blob b = new SerialBlob(encodeBase64Byte);



        Panne panne=new Panne();
        ServiceBMCI serviceBMCI=serviceRepository.findByNom(service);
        Materiel materiel1= materielRepository.findByNom(materiel);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(userDemande);
        Categorie categorie=categorieRepository.findByNom(typePanne);
        panne.setCategorie(categorie);
        panne.setEtatPanne(message.getEtat());
        panne.setDescription(message.getDescription());
        panne.setPhotos(compressBytes(message.getImage().getBytes()));


        Panne panne1=panneRepository.save(panne);
        List<Materiel>materielList=new ArrayList<>();
        if (panne1.getMateriels()==null){
            materielList.add(materiel1);
            panne1.setMateriels(materielList);
            panneRepository.save(panne1);


        }
        else {
            panne1.getMateriels().add(materiel1);
            panneRepository.save(panne1);
        }


        if (message != null) {

            demandeIntervention.setDate_Demande(new Date());
            demandeIntervention.setEtat_Demande("initiale");
            demandeIntervention.setPanne(panne1);
            demandeIntervention.setVisibiliter(false);
            demandeIntervention.setService(serviceBMCI);
            demandeIntervention.setUtilisateurs(utilisateur);
            Utilisateur utilisateur1=accountService.ServiceRespo(message.getService());
            String username=utilisateur1.getUsername();
            demandeRepository.save(demandeIntervention);

            count=demandeRepository.NombreDeNouveauMessage();

            this.template.convertAndSend("/topic/replay"+"/"+username,count);

        }
return count;*/

        }

    }
    @MessageExceptionHandler()
    @MessageMapping("/hello")

    public Long sendMessage(@RequestBody ModelDemande message ) throws IOException {
        int i=0;
        Long envoyer=null;
        DemandeIntervention demandeInterventionsNonVue=new DemandeIntervention();

        System.out.println(message.getImage().length());
        /*
        byte[] bdata = message.getImage().getBytes(1, (int)message.getImage().length());
        String data1 = new String(bdata);
        */
        //System.out.println(file.getOriginalFilename());
      /*  byte[]bdata= message.getImage().getBytes();
        System.out.println(bdata.length);
        Blob b = new SerialBlob(bdata);*/


        DemandeIntervention demandeIntervention=new DemandeIntervention();

        String userDemande=message.getUserDemander();
        System.out.println(userDemande);
        String typePanne =message.getType();
        String materiel=message.getMateriel();
        System.out.println(materiel);
        String service=message.getService();

        /* byte[] encodeBase64Byte = Base64.encodeBase64(message.getImage().getBytes());
        Blob b = new SerialBlob(encodeBase64Byte);
        */
        
        Panne panne=new Panne();
        ServiceBMCI serviceBMCI=serviceRepository.findByNom(service);
        Materiel materiel1= materielRepository.findByNom(materiel);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(userDemande);
        Categorie categorie=categorieRepository.findByNom(typePanne);
        panne.setCategorie(categorie);
        panne.setEtatPanne(message.getEtat());
        panne.setDescription(message.getDescription());
        panne.setPhotos(compressBytes(message.getImage().getBytes()));


        Panne panne1=panneRepository.save(panne);
        List<Materiel>materielList=new ArrayList<>();
        Materiel_Panne materiel_Panne=new Materiel_Panne();

        
        
       Materiel_Panne m1= materielPanneRepository.save(new Materiel_Panne(null,panne1,materiel1));
       materiel1.getMateriel_Pannes().add(m1);
       panne1.getMateriel_Pannes().add(m1);
       
     


        if (message != null) {


            demandeIntervention.setPanne(panne1);
            demandeIntervention.setVisibiliter(false);
            demandeIntervention.setService(serviceBMCI);
            demandeIntervention.setUtilisateurs(utilisateur);
            Utilisateur utilisateur1=accountService.ServiceRespo(message.getService());
            String username=utilisateur1.getUsername();
            demandeInterventionsNonVue=demandeRepository.save(demandeIntervention);

           // count=demandeRepository.NombreDeNouveauMessage();
             envoyer=demandeInterventionsNonVue.getId_Demande();
            System.out.println(envoyer);


            this.template.convertAndSend("/topic/replay"+"/"+username,envoyer);

        }




return  envoyer;
    }


    // compress the image bytes before storing it in the database
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


    @PostMapping  (value = "/interventionsimple")
    @ResponseBody
    public ModelMessage sendMeesageversEspace(@RequestBody  EnvoyerMessage message) throws IOException, SQLException ,EOFException{

        ModelMessage modelMessage=new ModelMessage();
        LinkedList<ModelMessage>modelMessageLinkedList=new LinkedList<ModelMessage>();
        System.out.println(message);
        Espace espace=espaceRepository.findById(message.getIdespace()).get();
        //  espace.getIntervention().getDemandeIntervention().getInterventions();

        System.out.println("votre espace"+espace.getIdEspace());
        // List<ModelMessage>modelMessages=new ArrayList<>();
        List<Utilisateur>utilisateurs= new ArrayList<>();
        if (espace!=null){
            utilisateurs= (List<Utilisateur>) espace.getUtilisateurs();
            modelMessage.setNom(message.getUsername());
            modelMessage.setMessage(message.getMessage());
            modelMessage.setDate(new Date());
            // modelMessages.add(modelMessage);
            modelMessageLinkedList.add(modelMessage);
            System.out.println(modelMessageLinkedList.size());
            if (espace.getCommentaire()==null) {
                System.out.println("commentaire vide");
                espace.setCommentaire(modelMessageLinkedList);
                System.out.println("size listCommentaire"+espace.getCommentaire().size());
                for(ModelMessage m:espace.getCommentaire()){
                    System.out.println("message"+m.getMessage()+"envoyerpar"+m.getNom());
                }

                //      espace.setCommentaire((LinkedList<ModelMessage>) modelMessages);
              //  Espace espace1=   espaceRepository.save(espace);
                for (Utilisateur u:espace.getUtilisateurs()){
                    this.template.convertAndSend("/topic/espace"+"/"+u.getUsername(),modelMessage);
                }

            }
            else {
                espace.getCommentaire().add(modelMessage);
             //   espaceRepository.save(espace);
                for (Utilisateur u:espace.getUtilisateurs()){
                    this.template.convertAndSend("/topic/espace"+"/"+u.getUsername(),modelMessage);
                }
            }
        }


        return  modelMessage;
    }

    @MessageMapping("/DemandeRejeter")
    public void DemandeRejeter(@PathVariable Long  demandeIntervention){

        DemandeIntervention demandeIntervention1=demandeRepository.findById(demandeIntervention).get();
        demandeIntervention1.setEtat_Demande("Rejeter");
        demandeIntervention1.setVisibiliter(false);
        accountService.DemandeRejeter(demandeIntervention1);
        String username=demandeIntervention1.getUtilisateurs().getUsername();
        this.template.convertAndSend("/topic/rejeterDemande"+"/"+username,demandeIntervention1);


    }



    @MessageExceptionHandler()
    @MessageMapping("/interventionComplexe")
    public  DemandeIntervention InterventionSimple(@RequestBody NotificationIntervenant notificationIntervenant){
        System.out.println(notificationIntervenant.getIdIntervention()+""+notificationIntervenant.getIntervenant());
        Utilisateur utilisateur=utilisateurRepository.findByUsername(notificationIntervenant.getIntervenant());
        Intervention  intervention=interventionRepository.findById(notificationIntervenant.getIdIntervention()).get();
        if (intervention!=null){
            System.out.println("intervention:"+intervention.getIdIntervention());
            if (intervention.getEspace().getUtilisateurs().contains(utilisateur)){
                this.template.convertAndSend("/topic/intervenant"+"/"+utilisateur.getUsername(),intervention.getDemandeIntervention());

            }
        }

        return intervention.getDemandeIntervention(); }
        @MessageExceptionHandler()
    @MessageMapping("/interventionsimple")
    public  ModelMessage InterventionSimple(EnvoyerMessage message){
        ModelMessage modelMessage=new ModelMessage();
        LinkedList<ModelMessage>modelMessageLinkedList=new LinkedList<ModelMessage>();
        System.out.println(message);
        Espace espace=espaceRepository.findById(message.getIdespace()).get();
        for(ModelMessage m:espace.getCommentaire()){

            System.out.println(m.getMessage());
        }
        System.out.println("votre espace"+espace.getIdEspace());
       // List<ModelMessage>modelMessages=new ArrayList<>();
        List<Utilisateur>utilisateurs= new ArrayList<>();
        if (espace!=null){
            utilisateurs= (List<Utilisateur>) espace.getUtilisateurs();
            modelMessage.setNom(message.getUsername());
            modelMessage.setMessage(message.getMessage());
            modelMessage.setDate(new Date());
           // modelMessages.add(modelMessage);
            modelMessageLinkedList.add(modelMessage);
            System.out.println(modelMessageLinkedList.size());
            if (espace.getCommentaire()==null) {
                System.out.println("commentaire vide");
                espace.setCommentaire(modelMessageLinkedList);
                System.out.println("size listCommentaire"+espace.getCommentaire().size());
                for(ModelMessage m:espace.getCommentaire()){
                    System.out.println("message"+m.getMessage()+"envoyerpar"+m.getNom());
                }

                //      espace.setCommentaire((LinkedList<ModelMessage>) modelMessages);
             Espace espace1=   espaceRepository.save(espace);
                for (Utilisateur u:espace1.getUtilisateurs()){
                    this.template.convertAndSend("/topic/espace"+"/"+u.getUsername(),modelMessage);
                }


            }
            else {
                espace.getCommentaire().add(modelMessage);
                espaceRepository.save(espace);
                for (Utilisateur u:espace.getUtilisateurs()){
                    this.template.convertAndSend("/topic/espace"+"/"+u.getUsername(),modelMessage);
                }

            }



        }


        return  modelMessage;

    }
        
   

   /*
    @PostMapping  (value = "/interventionsimple")

    @ResponseBody
    public ModelMessage sendMeesageversEspace(@RequestBody  EnvoyerMessage envoyerMessage) throws IOException, SQLException {

        ModelMessage modelMessage=new ModelMessage();
        System.out.println(envoyerMessage);
        Espace espace=espaceRepository.findById(envoyerMessage.getIdespace()).get();
        System.out.println("votre espace"+espace.getIdEspace());
        List<ModelMessage>modelMessages=new ArrayList<>();
        List<Utilisateur>utilisateurs= new ArrayList<>();

        if (espace!=null){
            utilisateurs= (List<Utilisateur>) espace.getUtilisateurs();
            modelMessage.setNom(envoyerMessage.getUsername());
            modelMessage.setMessage(envoyerMessage.getMessage());
            modelMessage.setDate(new Date());
            modelMessages.add(modelMessage);
            System.out.println();
            if (espace.getCommentaire()==null) {
                espace.setCommentaire((LinkedList<ModelMessage>) modelMessages);


          //      espace.setCommentaire((LinkedList<ModelMessage>) modelMessages);
                espaceRepository.save(espace);
                for (Utilisateur u:espace.getUtilisateurs()){
                    this.template.convertAndSend("/topic/replay"+"/"+u.getUsername(),modelMessage.getMessage());
                }


            }
            else {
                espace.getCommentaire().add(modelMessage);
                espaceRepository.save(espace);
                for (Utilisateur u:espace.getUtilisateurs()){
                    this.template.convertAndSend("/topic/replay"+"/"+u.getUsername(),modelMessage);
                }

            }



        }



        return  modelMessage;

    }
    
    
    




*/



}
