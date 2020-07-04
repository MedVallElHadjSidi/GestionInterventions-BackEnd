package com.example.demo;

import com.example.demo.DAO.*;
import com.example.demo.Entities.Adresse;
import com.example.demo.Entities.Agence;
import com.example.demo.Entities.Role;
import com.example.demo.Entities.Utilisateur;
import com.example.demo.Services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GestionInterventionsApplication implements CommandLineRunner {
	@Autowired
	private AdesseRepository adesseRepository;
	@Autowired
	private AgenceRepository agenceRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private MaterielRepository materielRepository;
	@Autowired
	private AccountService accountService;
	@Bean
	public BCryptPasswordEncoder getBcrypte(){
		return  new BCryptPasswordEncoder();
	}



	public static void main(String[] args) {
		SpringApplication.run(GestionInterventionsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		/*Utilisateur utilisateur=accountService.addUser(new Utilisateur("ADM","Mv2","elhadjsidimv@gmail.com","admin","1234",null,null,null,null,null));
		Role role =rolesRepository.save(new Role(null,"ADMIN"));
		accountService.AddRoles("admin","ADMIN");

*/



	}
}
