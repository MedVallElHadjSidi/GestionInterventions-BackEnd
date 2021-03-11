package com.example.demo.Entities;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Materiel_Panne implements Serializable {
	@Id @GeneratedValue
	private Long idPanneMateriel;
	@ManyToOne
    @JoinColumn(name = "Panne_ID")
	private Panne panne;
	@ManyToOne
    @JoinColumn(name = "Materiel_ID")
	private Materiel materiel;

}
