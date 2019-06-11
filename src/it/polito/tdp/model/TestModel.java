package it.polito.tdp.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		model.createGraph(2015);
		
		/* System.out.println("");
		List<District> districts = model.getDistrictsList();
		for(District d : districts) {
			List<Neighbor> neighbors = model.getNeighborsOf(d);
			System.out.println("Vicini distretto " + d + ":");
			System.out.println(neighbors);
			System.out.println("");
		} */
		
		Simulatore sim = new Simulatore(model);
		
		sim.init(2, 2015, 1, 14);
		
		sim.run();
		
		System.out.println("Casi gestiti: " + sim.getNumEventiGestiti());
		System.out.println("Casi NON gestiti: " + sim.getNumEventiMalGestiti());
	}

}
