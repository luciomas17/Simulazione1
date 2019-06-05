package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;

public class Model {
	
	private EventsDao dao;
	private Graph<District, DefaultWeightedEdge> graph;
	private List<District> districts;
	
	public Model() {
		this.dao = new EventsDao();
	}
	
	public void createGraph(int year) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		this.districts = dao.getDistrictByYear(year);
		Graphs.addAllVertices(this.graph, districts);
		
		for(District d1 : this.graph.vertexSet()) {
			for(District d2 : this.graph.vertexSet()) {
				if(!d1.equals(d2)) {
					Double distance = LatLngTool.distance(d1.getAvgLatLng(), d2.getAvgLatLng(), LengthUnit.KILOMETER);
					this.graph.addEdge(d1, d2);
					this.graph.setEdgeWeight(d1, d2, distance);
				}
			}
		}
		
		System.out.println("Grafo creato.");
		System.out.format("%d vertici, %d archi.\n", this.graph.vertexSet().size(), this.graph.edgeSet().size());
	}
	
	public List<Integer> getAnnoList() {
		return dao.getAnnoList();
	}
	
	public List<Neighbor> getNeighborsOf(District d) {
		List<District> neighbors = Graphs.neighborListOf(this.graph, d);
		List<Edge> edges = new ArrayList<>();
		List<Neighbor> neighborsOrdered = new ArrayList<>();
		
		for(District n : neighbors) {
			if(!d.equals(n)) {
				DefaultWeightedEdge edge = this.graph.getEdge(d, n);
				Double weight = this.graph.getEdgeWeight(edge);
				edges.add(new Edge(d, n, weight));
			}
		}
		Collections.sort(edges);
		
		for(Edge e : edges)
			neighborsOrdered.add(new Neighbor(e.getD2(), e.getWeight()));
			
		return neighborsOrdered;
	}
	
	public List<District> getDistrictsList() {
		return this.districts;
	}
	
}
