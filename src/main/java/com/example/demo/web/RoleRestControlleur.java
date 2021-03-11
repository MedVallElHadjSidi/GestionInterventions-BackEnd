package com.example.demo.web;

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

import com.example.demo.DAO.RolesRepository;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.ServiceBMCI;

@RestController
@RequestMapping("/RolesService")
public class RoleRestControlleur {
	@Autowired
	private RolesRepository rolesRepository;
	
	@GetMapping("/listRoles/{page}")
	public Page<Role>ListServiceBMCI(@PathVariable int page){
		
		   Pageable requestedPage = PageRequest.of(page, 5);
		   Page<Role>page2=rolesRepository.findAll(requestedPage);
		   
		   return page2;
	}
	
	@GetMapping("/ChercherRole/{id_Role}")
	public ResponseEntity<Role> chercherByServiceBMCI(@PathVariable Long id_Role) {
		Role role=new Role();
		role=rolesRepository.findById(id_Role).get();
		if(role!=null) {
			return ResponseEntity.ok().body(role);
		}
		
		return  ResponseEntity.ok().body(role);
		
	}
	@PutMapping("/EditRole/{id_Role}")
	public ResponseEntity<Role>editServiceBMCI(@PathVariable Long id_Role,@RequestBody Role role){
		
		Role roleBMCI=rolesRepository.findById(id_Role).get();
		if(roleBMCI!=null) {
			roleBMCI.setRoleName(role.getRoleName());
			return ResponseEntity.ok().body(rolesRepository.save(roleBMCI));
		}
		return ResponseEntity.ok().body(roleBMCI);
		
		
	}
	
}
