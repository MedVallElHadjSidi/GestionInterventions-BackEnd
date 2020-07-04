package com.example.demo.web;

import com.example.demo.Entities.Utilisateur;
import com.example.demo.Services.AccountService;
import com.example.demo.model.ModelUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicRestController {
    @Autowired
    private AccountService accountService;
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


}
