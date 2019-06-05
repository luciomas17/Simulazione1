package it.polito.tdp.model;

public class Edge implements Comparable<Edge> {

	private District d1;
	private District d2;
	private Double weight;
	
	public Edge(District d1, District d2, Double weight) {
		super();
		this.d1 = d1;
		this.d2 = d2;
		this.weight = weight;
	}

	public District getD1() {
		return d1;
	}

	public void setD1(District d1) {
		this.d1 = d1;
	}

	public District getD2() {
		return d2;
	}

	public void setD2(District d2) {
		this.d2 = d2;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((d1 == null) ? 0 : d1.hashCode());
		result = prime * result + ((d2 == null) ? 0 : d2.hashCode());
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
		Edge other = (Edge) obj;
		if (d1 == null) {
			if (other.d1 != null)
				return false;
		} else if (!d1.equals(other.d1))
			return false;
		if (d2 == null) {
			if (other.d2 != null)
				return false;
		} else if (!d2.equals(other.d2))
			return false;
		return true;
	}

	@Override
	public int compareTo(Edge other) {
		return (int)(this.weight - other.weight);
	}
	
}
