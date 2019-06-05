package it.polito.tdp.model;

public class Neighbor {
	
	private District d;
	private Double distance;
	
	public Neighbor(District d, Double distance) {
		super();
		this.d = d;
		this.distance = distance;
	}
	
	public District getD() {
		return d;
	}
	
	public void setD(District d) {
		this.d = d;
	}
	
	public Double getDistance() {
		return distance;
	}
	
	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((d == null) ? 0 : d.hashCode());
		result = prime * result + ((distance == null) ? 0 : distance.hashCode());
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
		Neighbor other = (Neighbor) obj;
		if (d == null) {
			if (other.d != null)
				return false;
		} else if (!d.equals(other.d))
			return false;
		if (distance == null) {
			if (other.distance != null)
				return false;
		} else if (!distance.equals(other.distance))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Distretto %d, distanza: %.3f km", d.getId(), distance);
	}

}
