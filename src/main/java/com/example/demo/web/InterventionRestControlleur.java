package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.DemandeRepository;
import com.example.demo.DAO.InterventionRepository;
import com.example.demo.Entities.DemandeIntervention;
import com.example.demo.Entities.Intervention;

@RestController
@CrossOrigin(origins = "*" ,allowedHeaders = "*")
public class InterventionRestControlleur {
	@Autowired
	public DemandeRepository demandeRepository;
	
	@GetMapping("/interventionsNonResolus/{page}")
	public Page<DemandeIntervention>chercherInterventionNonResolus(@PathVariable int page){
		 Pageable requestedPage = PageRequest.of(page, 5);
		 Page<DemandeIntervention>page2=demandeRepository.ServiceNonResolu(requestedPage);
		 System.out.println(page2.getContent().size());
		 for (DemandeIntervention d:page2.getContent()){
	            if(d.getInterventions().size()>0){
	                System.out.println("hello");
	            }}
		 
		return page2;
		
		
		
	}
	@GetMapping("/ConsulterHistoriqueServicesInfo/{page}")
	public Page<DemandeIntervention>ConsulterHistoriqueServicesInfo(@PathVariable int page){
		 Pageable requestedPage = PageRequest.of(page, 5);
		 return demandeRepository.ConsulterHistoriqueServicesInfo(requestedPage);
		
	}
	

}
