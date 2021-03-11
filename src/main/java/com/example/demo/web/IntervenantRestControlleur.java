package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.DemandeIntervention;
import com.example.demo.Services.AccountService;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class IntervenantRestControlleur {
	@Autowired
	public AccountService accountService;
	
	@GetMapping("/missionInterventionResolu/{username}")
	public List<DemandeIntervention>MisssionInterventionResolu(@PathVariable String username){
		List<DemandeIntervention>demandes =accountService.MissionAsoocierIntervenantResolue(username);
		for(DemandeIntervention d:demandes) {
			if(d.getInterventions()!=null) {
				System.out.println(d.getInterventions().size());
			}
		}
		return demandes;
	}
	
	@GetMapping("/missionInterventionNonResolu/{username}")
	public List<DemandeIntervention>MisssionInterventionNoResolu(@PathVariable String username){
		
		List<DemandeIntervention>demandes=accountService.MissionAsoocierIntervenantNonResolue(username);
		for(DemandeIntervention d:demandes) {
			if(d.getInterventions()!=null) {
				System.out.println(d.getInterventions().size());
			}
		}
		return demandes;
	}
	
	@GetMapping("/missionInterventionHistorique/{username}")
	public List<DemandeIntervention>MisssionInterventionHistorique(@PathVariable String username){
		List<DemandeIntervention>demandes =accountService.MissionAsoocierIntervenantHisrorique(username);
		for(DemandeIntervention d:demandes) {
			if(d.getInterventions()!=null) {
				System.out.println(d.getInterventions().size());
			}
		}
		return demandes;
	}
	

}
