package it.polito.tdp.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.model.Evento.TipoEvento;

public class Simulatore {
	
	public class PrioritaEventi implements Comparator<Evento> {
		@Override
		public int compare(Evento e1, Evento e2) {
			if(e1.getOrarioChiamata().isBefore(e2.getOrarioChiamata()))
				return -1;
			else
				return 1;
		}
	}
	
	private Model model;
	private PriorityQueue<Evento> queue;
	private Random random;
	
	private List<Event> eventiCriminosi;
	private int agentiLiberi;
	private Map<Integer, Agente> agenti;
	
	private double distanzaOrariaPercorribile;
	private Duration tempoGestioneEvento1;
	private Duration tempoGestioneEvento2;
	private Duration tempoGestioneAltriEventi;
	private int ritardoArrivoAgente;
	private int minutiAttesa;
	
	private int numEventiGestiti;
	private int numEventiMalGestiti;
	
	public Simulatore(Model model) {
		this.model = model;
		this.queue = new PriorityQueue<>(new PrioritaEventi());
		this.random = new Random();
	}
	
	public void init(int N, int anno, int mese, int giorno) {
		queue.clear();
		
		agentiLiberi = N;
		int districtId = model.determinaDistrettoMenoCriminoso(anno, mese, giorno);
		LatLng districtPos = model.determinaPosizioneDistretto(districtId);
		eventiCriminosi = model.listEventiCriminosi(anno, mese, giorno, districtId);
		agenti = new HashMap<>();
		for(int i = 1; i <= N; i ++)
			agenti.put(i, new Agente(i, districtPos, true));
		
		distanzaOrariaPercorribile = 60;
		tempoGestioneEvento1 = Duration.ofHours(1);
		tempoGestioneEvento2 = Duration.ofHours(2);
		tempoGestioneAltriEventi = Duration.ofHours(2);
		ritardoArrivoAgente = 15;
		minutiAttesa = 5;
		
		numEventiGestiti = 0;
		numEventiMalGestiti = 0;
		
		for(int i = 0; i < eventiCriminosi.size(); i ++)
			queue.add(new Evento(eventiCriminosi.get(i).getReportedDate(), ritardoArrivoAgente, eventiCriminosi.get(i).getPos(), 
					eventiCriminosi.get(i).getEventType(), TipoEvento.CHIAMATA, -1));
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Evento e = queue.poll();
			System.out.println(e);
			LocalDateTime ora = e.getOrarioChiamata();
			int ritardoAmmesso = e.getMinutiRitardoAmmessi();
			LatLng posChiamata = e.getPos();
			String crimine = e.getCrimine();
			int agenteId = e.getAgenteId();
			
			switch(e.getTipo()) {
				case CHIAMATA:
					if(agentiLiberi > 0) {
						agentiLiberi--;
						
						double distanzaMinima = -1;
						for(int i = 1; i <= agenti.size(); i ++) {
							if(agenti.get(i).isLibero()) {
								distanzaMinima = LatLngTool.distance(posChiamata, agenti.get(i).getPosAttuale(), LengthUnit.KILOMETER);
								break;
							}
						}
						int agentePiuVicino = -1;
						for(int i = 1; i <= agenti.size(); i ++) {
							if(agenti.get(i).isLibero()) {
								double distanzaAgente = LatLngTool.distance(posChiamata, agenti.get(i).getPosAttuale(), LengthUnit.KILOMETER);
								if(distanzaAgente <= distanzaMinima) {
									distanzaMinima = distanzaAgente;
									agentePiuVicino = agenti.get(i).getId();
								}
							}
						}
						agenti.get(agentePiuVicino).setLibero(false);
						queue.add(new Evento(ora, ritardoAmmesso, posChiamata, crimine, TipoEvento.IN_MOVIMENTO, agentePiuVicino));
						
					} else
						queue.add(new Evento(ora, ritardoAmmesso-minutiAttesa, posChiamata, crimine, TipoEvento.ATTESA, -1));
					
					break;
				
				case ATTESA:
					if(ritardoAmmesso > 0) {
						if(agentiLiberi > 0) {
							agentiLiberi--;
							
							double distanzaMinima = -1;
							for(int i = 1; i <= agenti.size(); i ++) {
								if(agenti.get(i).isLibero()) {
									distanzaMinima = LatLngTool.distance(posChiamata, agenti.get(i).getPosAttuale(), LengthUnit.KILOMETER);
									break;
								}
							}
							int agentePiuVicino = -1;
							for(int i = 1; i <= agenti.size(); i ++) {
								if(agenti.get(i).isLibero()) {
									double distanzaAgente = LatLngTool.distance(posChiamata, agenti.get(i).getPosAttuale(), LengthUnit.KILOMETER);
									if(distanzaAgente <= distanzaMinima) {
										distanzaMinima = distanzaAgente;
										agentePiuVicino = agenti.get(i).getId();
									}
								}
							}
							agenti.get(agentePiuVicino).setLibero(false);
							queue.add(new Evento(ora, ritardoAmmesso, posChiamata, crimine, TipoEvento.IN_MOVIMENTO, agenti.get(agentePiuVicino).getId()));
							
						} else
							queue.add(new Evento(ora, ritardoAmmesso-minutiAttesa, posChiamata, crimine, TipoEvento.ATTESA, -1));
						
					} else 
						queue.add(new Evento(ora, ritardoAmmesso, posChiamata, crimine, TipoEvento.RITARDO, -1));
					
					break;
					
				case IN_MOVIMENTO:
					double distanza = LatLngTool.distance(posChiamata, agenti.get(agenteId).getPosAttuale(), LengthUnit.KILOMETER);
					double distanzaPercorribleAlMinuto = distanzaOrariaPercorribile / 60;
					int tempoImpiegato = (int)(distanza / distanzaPercorribleAlMinuto);
					
					if(tempoImpiegato <= ritardoAmmesso)
						queue.add(new Evento(ora.plusMinutes(tempoImpiegato), ritardoAmmesso, posChiamata, crimine, TipoEvento.ARRIVATO, agenteId));
					else
						queue.add(new Evento(ora.plusMinutes(tempoImpiegato), ritardoAmmesso, posChiamata, crimine, TipoEvento.RITARDO, agenteId));
					
					break;
				
				case ARRIVATO:
					agenti.get(agenteId).setPosAttuale(posChiamata);
					
					if(crimine.equals("all_other_crimes")) {
						if(random.nextInt(1) == 0)
							queue.add(new Evento(ora.plus(tempoGestioneEvento1), ritardoAmmesso, posChiamata, crimine, TipoEvento.GESTITO, agenteId));
						else
							queue.add(new Evento(ora.plus(tempoGestioneEvento2), ritardoAmmesso, posChiamata, crimine, TipoEvento.GESTITO, agenteId));
					} else
						queue.add(new Evento(ora.plus(tempoGestioneAltriEventi), ritardoAmmesso, posChiamata, crimine, TipoEvento.GESTITO, agenteId));
					
					break;
					
				case RITARDO:
					numEventiMalGestiti++;
					
					if(agenteId != -1) {
						agenti.get(agenteId).setPosAttuale(posChiamata);
						agenti.get(agenteId).setLibero(true);
						agentiLiberi++;
					}
					
					break;
					
				case GESTITO:
					numEventiGestiti++;
					
					agenti.get(agenteId).setPosAttuale(posChiamata);
					agenti.get(agenteId).setLibero(true);
					agentiLiberi++;
					
					break;
			}	
		}
	}
	

	public int getNumEventiGestiti() {
		return numEventiGestiti;
	}
	

	public int getNumEventiMalGestiti() {
		return numEventiMalGestiti;
	}

}
