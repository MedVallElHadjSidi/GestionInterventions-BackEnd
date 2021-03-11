package com.example.demo.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.demo.Entities.Materiel_Panne;
import com.example.demo.Entities.Panne;

@Repository
@CrossOrigin(origins = "*",allowedHeaders = "*")
public interface MaterielPanneRepository extends JpaRepository<Materiel_Panne, Long> {
	public List<Materiel_Panne> findByMaterielNom(String m);
	public Materiel_Panne findByPanne(Panne p);

}
