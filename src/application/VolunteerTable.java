package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class VolunteerTable {
	private HashMap<Integer, Volunteer> volunteers = new HashMap<Integer, Volunteer>();
	private Connection connection;
	private final String SELECT_QUERY = "SELECT volunID, hours, courtOrdered, username, password, firstName,"
			+ " middleInitial, lastName, email, phone, gender, address, birthDate,"
			+ " emergencyContact FROM csc3610_Group3_finalProject.volunteers";
	
	private String update_query = "UPDATE csc3610_Group3_finalProject.volunteers SET"
			+ " hours = ?, courtOrdered = ?, username = ?, password = ?, firstName = ?, middleInitial = ?,"
			+ " lastName = ?, email = ?, phone = ?, gender = ?, address = ?,"
			+ " birthDate = ?, emergencyContact = ? WHERE volunID = ?;";
	
	private String delete_query = "DELETE FROM csc3610_Group3_finalProject.volunteers"
			+ " WHERE volunID = ?;";
	
	VolunteerTable(){
		setVolunteers();
	}
	
	public void setVolunteers() {
		setConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rset = stmt.executeQuery(SELECT_QUERY);
			
			
			while(rset.next()) {
				Volunteer temp = new Volunteer();
				temp.setVolunteerID(rset.getInt(1));
				temp.setHoursVolunteered(rset.getInt(2));
				temp.setCourtOrdered(rset.getBoolean(3));
				temp.setUserName(rset.getString(4));
				temp.setPassword(rset.getString(5));
				temp.setFirstName(rset.getString(6));
				temp.setMiddleInitial(rset.getString(7));
				temp.setLastName(rset.getString(8));
				temp.setEmail(rset.getString(9));
				temp.setPhone(rset.getString(10));
				temp.setGender(rset.getString(11));
				temp.setAddress(rset.getString(12));
				temp.setBirthDate(rset.getString(13));
				temp.setEmergencyContact(rset.getString(14));
				volunteers.put(temp.getVolunteerID(), temp);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public HashMap<Integer, Volunteer> getVolunteers() {
		return volunteers;
	}
	
	public Volunteer getVolunteerByID(int id) {
		return volunteers.get(id);
	}
	
	public void setConnection() {
		DBVolunteer volun = new DBVolunteer();
		this.connection = volun.getConnection();
	}
	
	public void updateVolunteer(Volunteer volun) {
		try {
			PreparedStatement prepared = connection.prepareStatement(update_query);
			prepared.setInt(1, volun.getHoursVolunteered());
			prepared.setBoolean(2, volun.getCourtOrdered());
			prepared.setString(3, volun.getUserName());
			prepared.setString(4, volun.getPassword());
			prepared.setString(5, volun.getFirstName());
			prepared.setString(6, volun.getMiddleInitial());
			prepared.setString(7, volun.getLastName());
			prepared.setString(8, volun.getEmail());
			prepared.setString(9, volun.getPhone());
			prepared.setString(10, volun.getGender());
			prepared.setString(11, volun.getAddress());
			prepared.setString(12, volun.getBirthDate());
			prepared.setString(13, volun.getEmergencyContact());
			prepared.setInt(14, volun.getVolunteerID());
			
			prepared.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public void delete(int num) {
		try {
			PreparedStatement prepared = connection.prepareStatement(delete_query);
			prepared.setInt(1, num);
			prepared.execute();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
}
