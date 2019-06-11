package it.polito.tdp.model;

import com.javadocmd.simplelatlng.LatLng;

public class Agente {
	
	private int id;
	private LatLng posAttuale;
	private boolean libero;
	
	public Agente(int id, LatLng posAttuale, boolean libero) {
		super();
		this.id = id;
		this.posAttuale = posAttuale;
		this.libero = libero;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LatLng getPosAttuale() {
		return posAttuale;
	}

	public void setPosAttuale(LatLng posAttuale) {
		this.posAttuale = posAttuale;
	}

	public boolean isLibero() {
		return libero;
	}

	public void setLibero(boolean libero) {
		this.libero = libero;
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
		Agente other = (Agente) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Agente [id=%s, posAttuale=%s, libero=%s]", id, posAttuale, libero);
	}
	
}
