package com.example.demo.model;

public class StatistiqueTableauBordRespoInfo {
	
	private String label;
	private int nbre;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getNbre() {
		return nbre;
	}
	public void setNbre(int nbre) {
		this.nbre = nbre;
	}
	public StatistiqueTableauBordRespoInfo(String label, int nbre) {
		super();
		this.label = label;
		this.nbre = nbre;
	}
	public StatistiqueTableauBordRespoInfo() {
		super();
	}
	
	

}
