package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.UtilisateurRepository;
import com.example.demo.Entities.Adresse;
import com.example.demo.Entities.Agence;
import com.example.demo.Entities.Utilisateur;

@RestController
@CrossOrigin
@RequestMapping("/Utilisateur")
public class UtilisateurRestControlleur {
	@Autowired
	private UtilisateurRepository utilisateurRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	@GetMapping("/listeUsers/{page}")
	public Page<Utilisateur>ListUsers(@PathVariable int page){
		 Pageable requestedPage = PageRequest.of(page, 5);
		   Page<Utilisateur>page2=utilisateurRepository.findAll(requestedPage);
		   return page2;
		   
	}
	@GetMapping("/chercherUser/{code}")
	public ResponseEntity<Utilisateur> chercherByServiceBMCI(@PathVariable String code) {
		Utilisateur bmci=new Utilisateur();
		bmci=utilisateurRepository.findById(code).get();
		if(bmci!=null) {
			return ResponseEntity.ok().body(bmci);
		}
		
		return  ResponseEntity.ok().body(bmci);
		
	}
	
	@PutMapping("/EditUser/{code}")
	public ResponseEntity<Utilisateur>editUserBMCI(@PathVariable String code,@RequestBody Utilisateur utilisateur){
		
		Utilisateur utilisateur2=utilisateurRepository.findById(code).get();
		if(utilisateur2!=null) {
			utilisateur2.setNom(utilisateur.getNom());
			utilisateur2.setEmail(utilisateur.getEmail());
			utilisateur2.setEtat(utilisateur.getEtat());
			utilisateur2.setUsername(utilisateur.getUsername());
			if(!utilisateur.getPassword().equals("")) {
		        String passwdBrpt=bCryptPasswordEncoder.encode(utilisateur.getPassword());
		        utilisateur2.setPassword(passwdBrpt);
			}
		
			return ResponseEntity.ok().body(utilisateurRepository.save(utilisateur2));
		}
		return ResponseEntity.ok().body(utilisateur2);
		
		
	}

}
