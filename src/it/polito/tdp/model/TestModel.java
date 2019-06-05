package it.polito.tdp.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.createGraph(2015);
		
		System.out.println("");
		List<District> districts = model.getDistrictsList();
		for(District d : districts) {
			List<District> neighbors = model.getNeighborsOf(d);
			System.out.println("Vicini distretto " + d + ":");
			System.out.println(neighbors);
			System.out.println("");
		}
	}

}
