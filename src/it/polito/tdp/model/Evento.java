package it.polito.tdp.model;

import java.time.LocalDateTime;

import com.javadocmd.simplelatlng.LatLng;

public class Evento {
	
	public enum TipoEvento {
		CHIAMATA,
		ATTESA,
		IN_MOVIMENTO,
		ARRIVATO,
		RITARDO,
		GESTITO
	}
	
	private LocalDateTime orarioChiamata;
	private int minutiRitardoAmmessi;
	private LatLng pos;
	private String crimine;
	private TipoEvento tipo;
	private int agenteId;
	
	
	public Evento(LocalDateTime orarioChiamata, int minutiRitardoAmmessi, LatLng pos, String crimine, TipoEvento tipo, int agenteId) {
		super();
		this.orarioChiamata = orarioChiamata;
		this.minutiRitardoAmmessi = minutiRitardoAmmessi;
		this.pos = pos;
		this.crimine = crimine;
		this.tipo = tipo;
		this.agenteId = agenteId;
	}

	public LocalDateTime getOrarioChiamata() {
		return orarioChiamata;
	}

	public TipoEvento getTipo() {
		return tipo;
	}

	public LatLng getPos() {
		return pos;
	}

	public String getCrimine() {
		return crimine;
	}

	public int getAgenteId() {
		return agenteId;
	}

	public int getMinutiRitardoAmmessi() {
		return minutiRitardoAmmessi;
	}

	@Override
	public String toString() {
		if(agenteId == -1)
			return String.format(
				"Evento [orarioChiamata=%s, minutiRitardoAmmessi=%s, pos=%s, crimine=%s, tipo=%s, agenteId=null]",
				orarioChiamata, minutiRitardoAmmessi, pos, crimine, tipo);
		else
			return String.format(
					"Evento [orarioChiamata=%s, minutiRitardoAmmessi=%s, pos=%s, crimine=%s, tipo=%s, agenteId=%s]",
					orarioChiamata, minutiRitardoAmmessi, pos, crimine, tipo, agenteId);
	}
	
	
	
}
