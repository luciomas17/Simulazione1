package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.model.District;
import it.polito.tdp.model.Event;


public class EventsDao {
	
	public List<Integer> getAnnoList() {
		String sql = "SELECT YEAR(reported_date) AS res FROM events GROUP BY res ORDER BY res";
		List<Integer> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				try {
					result.add(res.getInt("res"));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<District> getDistrictByYear(int year) {
		String sql = "SELECT district_id, AVG(geo_lat) AS avgLat, AVG(geo_lon) AS avgLng " + 
				"FROM events WHERE YEAR(reported_date) = ? " + 
				"GROUP BY district_id";
		List<District> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			st.setInt(1, year);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				try {
					LatLng avgLatLng = new LatLng(res.getDouble("avgLat"), res.getDouble("avgLng"));
					result.add(new District(res.getInt("district_id"), avgLatLng));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int determinaDistrettoMenoCriminoso(int anno, int mese, int giorno) {
		String sql = "SELECT district_id, COUNT(incident_id) AS numEvents " + 
				"FROM events " + 
				"WHERE YEAR(reported_date) = ? AND MONTH(reported_date) = ? AND DAY(reported_date) = ? " + 
				"GROUP BY district_id " + 
				"ORDER BY numEvents";
		int districtId = -1;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			ResultSet res = st.executeQuery();
			
			if(res.next()) {
				try {
					districtId = res.getInt("district_id");
				
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return districtId;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<Event> listEventiCriminosi(int anno, int mese, int giorno, int districtId) {
		String sql = "SELECT incident_id, reported_date, offense_category_id, geo_lat, geo_lon " + 
				"FROM events " + 
				"WHERE YEAR(reported_date) = ? AND MONTH(reported_date) = ? AND DAY(reported_date) = ? AND district_id = ? " + 
				"ORDER BY reported_date";
		List<Event> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			st.setInt(4, districtId);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				try {
					LatLng pos = new LatLng(res.getDouble("geo_lat"), res.getDouble("geo_lon"));
					result.add(new Event(res.getLong("incident_id"), res.getTimestamp("reported_date").toLocalDateTime(), 
							res.getString("offense_category_id"), pos));
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
