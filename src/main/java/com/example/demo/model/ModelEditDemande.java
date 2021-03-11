package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  @AllArgsConstructor
public class ModelEditDemande {
	private Long id;
	private  String type;
	private String etat;
	private String materiel;
	private String description;
	private String image;
	private String service;
	private String userDemander;


}
