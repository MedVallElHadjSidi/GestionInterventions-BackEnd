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

import com.example.demo.DAO.ServiceRepository;
import com.example.demo.Entities.ServiceBMCI;
import com.example.demo.model.ModelService;

@RestController
@CrossOrigin
@RequestMapping("/ServiceBMCI")
public class ServiceRestControlleur {
	@Autowired
	private ServiceRepository serviceRepository;
	@GetMapping("/listServixes/{page}")
	public Page<ServiceBMCI>ListServiceBMCI(@PathVariable int page){
		
		   Pageable requestedPage = PageRequest.of(page, 5);
		   Page<ServiceBMCI>page2=serviceRepository.findAll(requestedPage);
		   
		   return page2;
	}
	
	@GetMapping("/editService/{idService}")
	public ResponseEntity<ServiceBMCI> chercherByServiceBMCI(@PathVariable String idService) {
		ServiceBMCI bmci=new ServiceBMCI();
		bmci=serviceRepository.findById(idService).get();
		if(bmci!=null) {
			return ResponseEntity.ok().body(bmci);
		}
		
		return  ResponseEntity.ok().body(bmci);
		
	}
	@PutMapping("/EditService/{idService}")
	public ResponseEntity<ServiceBMCI>editServiceBMCI(@PathVariable String idService,@RequestBody ServiceBMCI modelService){
		
		ServiceBMCI serviceBMCI=serviceRepository.findById(idService).get();
		if(serviceBMCI!=null) {
			serviceBMCI.setNom(modelService.getNom());
			return ResponseEntity.ok().body(serviceRepository.save(serviceBMCI));
		}
		return ResponseEntity.ok().body(serviceBMCI);
		
		
	}
	
	
	

}
 