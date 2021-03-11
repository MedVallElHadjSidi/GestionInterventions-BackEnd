package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.AdesseRepository;
import com.example.demo.DAO.AgenceRepository;
import com.example.demo.Entities.Adresse;
import com.example.demo.Entities.Agence;
import com.example.demo.Entities.ServiceBMCI;


@RestController
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
@RequestMapping("/AgenceBMCI")
public class AgenceRestControlleur {
	@Autowired
	private AgenceRepository agenceRepository;
	@Autowired
	private AdesseRepository adresseRepository;
	
	@GetMapping("/MesAgences/{page}")
	public Page<Agence>ListAgences(@PathVariable int page){
		   Pageable requestedPage = PageRequest.of(page, 5);
		   Page<Agence>page2=agenceRepository.findAll(requestedPage);
		   
		   return page2;
		
	}
	@GetMapping("/editAgence/{idAgence}")
	public ResponseEntity<Agence> chercherByServiceBMCI(@PathVariable Long idAgence) {
		Agence bmci=new Agence();
		bmci=agenceRepository.findById(idAgence).get();
		if(bmci!=null) {
			return ResponseEntity.ok().body(bmci);
		}
		
		return  ResponseEntity.ok().body(bmci);
		
	}
	@PutMapping("/EditAgence/{idAgence}")
	public ResponseEntity<Agence>editServiceBMCI(@PathVariable Long idAgence,@RequestBody Agence agence){
		System.out.println(agence.toString());
	
		Agence agence1=agenceRepository.findById(idAgence).get();
		if(agence1!=null) {
			Adresse addresse=agence.getAdresse();
			addresse.setCommune(agence.getAdresse().getCommune());
			addresse.setWilaye(agence.getAdresse().getWilaye());
			addresse.setVille(agence.getAdresse().getVille());
			addresse.setRue(agence.getAdresse().getRue());
			Adresse adresse=adresseRepository.save(addresse);
			agence1.setAdresse(adresse);
			agence1.setNomAgence(agence.getNomAgence());
		
			
			
			
			
			return ResponseEntity.ok().body(agenceRepository.save(agence1));
		}
		return ResponseEntity.ok().body(agence1);
		
		
	}

}
