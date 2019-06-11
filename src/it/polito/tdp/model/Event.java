package it.polito.tdp.model;

import java.time.LocalDateTime;

import com.javadocmd.simplelatlng.LatLng;

public class Event {
	
	private long eventId;
	private LocalDateTime reportedDate;
	private String eventType;
	private LatLng pos;
	
	public Event(long eventId, LocalDateTime reportedDate, String eventType, LatLng pos) {
		super();
		this.eventId = eventId;
		this.reportedDate = reportedDate;
		this.eventType = eventType;
		this.pos = pos;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public LocalDateTime getReportedDate() {
		return reportedDate;
	}

	public void setReportedDate(LocalDateTime reportedDate) {
		this.reportedDate = reportedDate;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public LatLng getPos() {
		return pos;
	}

	public void setPos(LatLng pos) {
		this.pos = pos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (eventId ^ (eventId >>> 32));
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
		Event other = (Event) obj;
		if (eventId != other.eventId)
			return false;
		return true;
	}
	
}
