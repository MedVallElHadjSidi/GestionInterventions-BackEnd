package com.example.demo.DAO;

import com.example.demo.Entities.DemandeIntervention;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeRepository  extends JpaRepository<DemandeIntervention,Long> {
}
