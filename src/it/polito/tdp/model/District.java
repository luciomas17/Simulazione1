package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;

public class District {

	private int id;
	private LatLng avgLatLng;
	
	public District(int id, LatLng avgLatLng) {
		super();
		this.id = id;
		this.avgLatLng = avgLatLng;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LatLng getAvgLatLng() {
		return avgLatLng;
	}

	public void setAvgLatLng(LatLng avgLatLng) {
		this.avgLatLng = avgLatLng;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		District other = (District) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Distretto " + id;
	}
	
}
