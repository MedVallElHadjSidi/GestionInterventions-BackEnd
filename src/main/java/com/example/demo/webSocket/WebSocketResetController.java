package com.example.demo.webSocket;


import com.example.demo.DAO.*;
import com.example.demo.Entities.*;
import com.example.demo.Services.AccountService;
import com.example.demo.model.ModelDemande;


import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;


import org.springframework.messaging.simp.SimpMessagingTemplate;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.sql.rowset.serial.SerialBlob;
import java.io.*;


import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.zip.Deflater;

@RestController
@RequestMapping(value = "/app")
@CrossOrigin(origins = "*")
@Transactional
public class WebSocketResetController {
    int count=0;

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


    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketResetController(SimpMessagingTemplate template) {
        this.template = template;
    }
    @PostMapping  (value = "/envoyer")
    @ResponseBody
    public int sendReset(@RequestBody  ModelDemande message) throws IOException, SQLException {

        int i=0;
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



return count;

    }


    @MessageExceptionHandler()
    @MessageMapping("/hello")

    public int sendMessage(ModelDemande message, byte[] messagebyte ) throws IOException {
        int i=0;

        System.out.println(messagebyte.length);



        DemandeIntervention demandeIntervention=new DemandeIntervention();

        String userDemande=message.getUserDemander();
        System.out.println(userDemande);
        String typePanne =message.getType();
        String materiel=message.getMateriel();
        System.out.println(materiel);
        String service=message.getService();


        Panne panne=new Panne();
        ServiceBMCI serviceBMCI=serviceRepository.findByNom(service);
        Materiel materiel1= materielRepository.findByNom(materiel);
        Utilisateur utilisateur=utilisateurRepository.findByUsername(userDemande);
        Categorie categorie=categorieRepository.findByNom(typePanne);
        panne.setCategorie(categorie);
        panne.setEtatPanne(message.getEtat());
        panne.setDescription(message.getDescription());
       //panne.setPhotos(message.getImage());
     //   panne.setPhotosBytes(compressBytes(messagebyte));

       Panne panne1=panneRepository.save(panne);
        List<Materiel>materielList=new ArrayList<>();
        if (panne1.getMateriels()==null){
            materielList.add(materiel1);
            panne1.setMateriels(materielList);

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





return  count;
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







}
