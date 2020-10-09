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
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
	@Autowired
	private RepositoryRestConfiguration restConfiguration;
	@Bean
	public BCryptPasswordEncoder getBcrypte(){
		return  new BCryptPasswordEncoder();
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
						.allowedOrigins("http://localhost:4200'");
			}
		};
	}



	public static void main(String[] args) {
		SpringApplication.run(GestionInterventionsApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

/*
		Utilisateur utilisateur=accountService.addUser(new Utilisateur("adm1","MedVall","MedVall@gmail.com","admin","1234",null,null,null,null,null));
		Role role =rolesRepository.save(new Role(null,"ADMIN"));
		accountService.AddRoles("admin","ADMIN");




		Utilisateur utilisateur2=accountService.addUser(new Utilisateur("respo","DVall","elhadjsidimv@gmail.com","responsable","1234",null,null,null,null,null));
		Role role2 =rolesRepository.save(new Role(null,"RESPONSABLE"));
		accountService.AddRoles("responsable","RESPONSABLE");*/

		/*Adresse ad1=adesseRepository.save(new Adresse(null,"nktt","ARAFAT","NKTT","Meka"));
		Agence agence=agenceRepository.save(new Agence(null,"agence1",ad1,null));
		Utilisateur utilisateur=accountService.addUser(new Utilisateur("user1","MedAbdou","abdou@gmail.com","MedVall","1234",agence,null,null,null,null));
		Role role =rolesRepository.save(new Role(null,"S-USER"));
		accountService.AddRoles("MedVall","S-USER");*/

	}

}
