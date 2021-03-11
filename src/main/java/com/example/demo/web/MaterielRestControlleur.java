package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.MaterielPanneRepository;
import com.example.demo.DAO.MaterielRepository;
import com.example.demo.DAO.PanneRepository;
import com.example.demo.Entities.Materiel;
import com.example.demo.Entities.Panne;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.ServiceBMCI;

@RestController
@RequestMapping("/materielService")
public class MaterielRestControlleur {
	@Autowired
	private MaterielRepository materielRepository;
	@Autowired 
	private PanneRepository panneRepository;
	
	@Autowired
	private MaterielPanneRepository materielPanneRepository;
	
	
	@GetMapping("/materielNames")
	public List<String> StatistiquesMaterielesNames(){
		List<String>materielsNames=new ArrayList<String>();
		List<Materiel>materiels=materielRepository.findAll();
		for(Materiel m:materiels) {
			materielsNames.add(m.getNom());
			
		}
		return materielsNames;
	}
	
	@GetMapping("/listMateriels/{page}")
	public Page<Materiel>ListServiceBMCI(@PathVariable int page){
		
		   Pageable requestedPage = PageRequest.of(page, 5);
		   Page<Materiel>page2=materielRepository.findAll(requestedPage);
		   
		   return page2;
	}
	
	
	
	@GetMapping("/chercherMateriel/{idMateriel}")
	public ResponseEntity<Materiel> chercherByServiceBMCI(@PathVariable Long idMateriel) {
		Materiel bmci=new Materiel();
		bmci=materielRepository.findById(idMateriel).get();
		if(bmci!=null) {
			return ResponseEntity.ok().body(bmci);
		}
		
		return  ResponseEntity.ok().body(bmci);
		
	}
	@PutMapping("/EditMateriel/{idMateriel}")
	public ResponseEntity<Materiel>editServiceBMCI(@PathVariable Long idMateriel,@RequestBody Materiel materiel){
		
		Materiel materielBMCI=materielRepository.findById(idMateriel).get();
		if(materiel!=null) {
			materielBMCI.setNom(materiel.getNom());
			materielBMCI.setModel(materiel.getModel());
			materielBMCI.setProcesseur(materiel.getProcesseur());
			return ResponseEntity.ok().body(materielRepository.save(materielBMCI));
		}
		return ResponseEntity.ok().body(materielBMCI);
		
		
	}
	
	@GetMapping("/chercherMaterielpANNE/{idPanne}")
	public ResponseEntity<Materiel> chercherMaterielPanneBMCI(@PathVariable Long idPanne) {
		Materiel materielBmci=new Materiel();
		Panne bmci=new Panne();
		bmci=panneRepository.findById(idPanne).get();
		if(bmci!=null) {
			materielBmci=materielPanneRepository.findByPanne(bmci).getMateriel();
			System.out.println(materielBmci.getNom());
			
			return ResponseEntity.ok().body(materielBmci);
		}
		
		return  ResponseEntity.ok().body(materielBmci);
		
	}
	

}
