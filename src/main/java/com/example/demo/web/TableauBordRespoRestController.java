package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DAO.DemandeRepository;
import com.example.demo.DAO.InterventionRepository;
import com.example.demo.model.StatistiqueTableauBordRespoInfo;

@RestController
@RequestMapping("/tableaudebord")
public class TableauBordRespoRestController {
	@Autowired
	private InterventionRepository interventionRepository;
	@Autowired
	private DemandeRepository demandeRepository;
	@GetMapping("/tableauBordInfo")
	public List<StatistiqueTableauBordRespoInfo> tableauBordRespo(){
		
		List<StatistiqueTableauBordRespoInfo>tableauBordRespoInfos=new ArrayList<>();
		tableauBordRespoInfos.add(new StatistiqueTableauBordRespoInfo("InterventionEncours",interventionRepository.NmobreServcieEnCours() ));
		tableauBordRespoInfos.add(new StatistiqueTableauBordRespoInfo("InterventionResolu", interventionRepository.NmobreServcieResolu()));
		tableauBordRespoInfos.add(new StatistiqueTableauBordRespoInfo("InterventionNonResolu",interventionRepository.NmobreServcieNonResolu()));
		
		
		
		return tableauBordRespoInfos;
	}
	@GetMapping("/tableauBordDG")
	public List<StatistiqueTableauBordRespoInfo> tableauBordDG(){
		
		List<StatistiqueTableauBordRespoInfo>tableauBordRespoInfos=new ArrayList<>();
		tableauBordRespoInfos.add(new StatistiqueTableauBordRespoInfo("DemandeEncours",interventionRepository.NmobreServcieEnCours() ));
		tableauBordRespoInfos.add(new StatistiqueTableauBordRespoInfo("DemandeResolu", interventionRepository.NmobreServcieResolu()));
		tableauBordRespoInfos.add(new StatistiqueTableauBordRespoInfo("DemandeNonResolu",interventionRepository.NmobreServcieNonResolu()));
		tableauBordRespoInfos.add(new StatistiqueTableauBordRespoInfo("DemandeRejeter",demandeRepository.DemandeRejeter().size()));
		
		
		
		return tableauBordRespoInfos;
	}
}
