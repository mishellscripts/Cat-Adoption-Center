import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CatAdoptionModel {
	private Connection connection;
	private int locID;
	
	public CatAdoptionModel() {
		DataSource ds = DataSourceFactory.getMySQLDataSource();
	    try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}
	
	public void setLocation(int id) {
		locID = id;
	}
	
	public int getLocation() {
		return locID;
	}
	
	/**
	 * #1 PEOPLE can adopt a CAT from a ADOPTION_CENTER.
	 * @param pID
	 * @param cID
	 */
	public void adoptCat(int pID, int cID) {
		//TODO
	}
	
	/**
	 * PEOPLE can donate their CAT to a ADOPTION_CENTER.
	 * @param name
	 * @param age
	 * @param gender
	 * @param breed
	 * @param locID
	 */
	public void donateCat(String name, int age, String gender, String breed, int locID) {
		//TODO
	}
	
	/**
	 * #3 PEOPLE can search for a ADOPTION_CENTER.
	 * STORED PROCEDURE
	 * @param location the location entered by user
	 * 
	 */
	public int searchAdoptionCenter(String location) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_adoption_center(?)}");
		cs.setString(1, location);
		ResultSet rs = cs.executeQuery();
		int locID = 0;
		
		if (rs.next()) {
			locID = rs.getInt("locID"); 
			//String loc = rs.getString("location"); 
			//System.out.println("locID:" + locID + " Location:" + loc);
		}
		return locID;
	}

	/**
	 * #4 PEOPLE can search for a CAT from a ADOPTION_CENTER they are currently visiting.
	 * Stored procedure
	 * @param id the location id
	 */
	public void searchAdoptionCenterCats(int id) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_adoption_center_cats(?)}");
		cs.setInt(1, id);
		ResultSet rs = cs.executeQuery();
	}

	/**
	 * #5 PEOPLE can view all CAT�s MEDICAL records from the ADOPTION_CENTER they are currently visiting
	 * STORED PROCEDURE
	 * @param id the location id
	 */
	public void searchAdoptionCenterRecords(int id) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_adoption_center_records(?)}");
		cs.setInt(1, id);
		ResultSet rs = cs.executeQuery();
	}

	/**
	 * #6 PEOPLE can return the CAT to the ADOPTION_CENTER they adopted from.
	 * STORED PROCEDURE
	 * @param id the cat id
	 */
	public void returnCat(int id) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL return_cat(?)}");
		cs.setInt(1, id);
		ResultSet rs = cs.executeQuery();
	}

	/**
	 * #7 ADOPTION_CENTER can view all CAT�s and their MEDICAL record
	 * STORED PROCEDURE
	 * TODO: procedure has been altered as of 11/15
	 * @throws SQLException
	 */
	public void searchCats() throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_records()}");
		ResultSet rs = cs.executeQuery();
		while(rs.next()) {
			System.out.println("hello");
			int cID = rs.getInt("cID"); 
			String name = rs.getString("name"); 
			int age = rs.getInt("age");
			String gender = rs.getString("gender");
			String breed = rs.getString("breed");
			String disease = rs.getString("disease");
			System.out.println("cID:" + cID + " Name:" + name + " Age:" + age
					+ " Gender:" + gender + " Breed:" + breed + " Disease:" + disease);
		}
		
		/*
		Statement stmt = null; 
		try { stmt = connection .createStatement(); } 
			catch (SQLException e) { 
			String sql = "SELECT c.cID, c.cName, c.age, c.gender, c.breed, m.disease "
					+ "FROM cat c LEFT OUTER JOIN medical m USING(cID)";
			Statement st = connection.createStatement();
			//boolean hasResults = st.execute("{CALL search_records()}");
			boolean hasResults = st.execute(sql);
			while (hasResults) {
				ResultSet rs = st.getResultSet();
				while (rs.next()) {
					int cID = rs.getInt("cID"); 
					String name = rs.getString("name"); 
					int age = rs.getInt("age");
					String gender = rs.getString("gender");
					String breed = rs.getString("breed");
					String disease = rs.getString("disease");
					System.out.println("cID:" + cID + " Name:" + name + " Age:" + age
							+ " Gender:" + gender + " Breed:" + breed + " Disease:" + disease);
				}
				hasResults = st.getMoreResults();
			}	
		} 
		finally { 
			stmt.close(); 
		}*/
	}

	/**
	 * #8 ADOPTION_CENTER can match PEOPLE to a CAT based on preference.
	 * @parameter hashmap from search filters. if user wants a female tabby, the hashmap
	 * 	sent here will look like (breed, tabby), (gender, female)
	 */
	public void searchCatByPreference(HashMap<String, String> preferences) throws SQLException {
		String filters = "";
		int i = 0;
		for (String k : preferences.keySet()) {
			filters = filters + k + " = " + preferences.get(k);
			if (i < preferences.keySet().size() - 1) {
				filters = filters + " AND ";
			}
		}
		String query = "SELECT cID, NAME " //TODO: return more columns?
				+ "FROM CAT WHERE " + filters;
		Statement st = (Statement) connection.createStatement();
		st.executeQuery(query);
		
	}

	/**
	 * #9 ADOPTION_CENTER can register a found CAT.
	 * STORED PROCEDURE
	 * @param name
	 * @param age
	 * @param gender
	 * @param fee
	 * @param locID
	 * @throws SQLException
	 */
	public void insertCat(String name, int age, String gender, double fee, int locID) throws SQLException {
		// referencing slide 32 of JDBC
		PreparedStatement ps = null;
		String query = "INSERT INTO Cat VALUES (?,?,?,?,?)";
		ps.setString(1, name);
		ps.setInt(2, age);
		ps.setString(3, gender);
		ps.setDouble(4, fee);
		ps.setInt(5, locID);
		int test = ps.executeUpdate(); //TODO: take out the int test during finalization
		System.out.println("Affected rows insertCat: " + test);
	}

	/**
	 * TODO: determine whether #10 need methods or are purely for triggers
	 */

	/**
	 * #11 ADOPTION_CENTER can remove a CAT�s MEDICAL record. 
	 * @param cID cat ID
	 */
	public void removeMedical(int cID) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL remove_cat_medical_record(?)}");
		cs.setInt(1, cID);
		int test = cs.executeUpdate();
		System.out.println("Affected rows removeMedical: " + test);
	}

	/**
	 * #12 ADOPTION_CENTER can register a CAT�s MEDICAL record.
	 * @param cID
	 * @param disease
	 * @param fee
	 * @throws SQLException
	 */
	public void addMedical(int cID, String disease, double fee) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL register_cat_medical_record(?,?,?)}");
		cs.setInt(1, cID);
		cs.setString(2, disease);
		cs.setDouble(3, fee);
		int test = cs.executeUpdate();
		System.out.println("Affected rows addMedical: " + test);
	}

	/**
	 * #13 ADOPTION_CENTER can update a CAT�s MEDICAL record.
	 * @param cID
	 * @param disease
	 * @param fee
	 * @throws SQLException
	 */
	public void updateMedical(int cID, String disease, double fee)  throws SQLException  {
		CallableStatement cs = connection.prepareCall("{CALL update_cat_medical_fee(?,?,?)}");
		cs.setInt(1,cID);
		cs.setString(2, disease);
		cs.setDouble(3, fee);
		int test = cs.executeUpdate();
		System.out.println("Affected rows updateMedical: " + test);
	}

	/**
	 * #14 ADOPTION_CENTER can view all ADOPTION records.
	 * @throws SQLException
	 */
	public void viewAllAdoptions() throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL view_all_adoptions()}");
		ResultSet rs = cs.executeQuery();
	}

	/**
	 * #15 ADOPTION_CENTER can determine if the person (PEOPLE) is qualified to adopt
	 * a specific CAT based on person experience level.
	 * @throws SQLException
	 */
	public void isPersonQualified() throws SQLException {
		//TODO
	}

	/**
	 * #16 ADOPTION_CENTER can determine if the CAT is adoptable based
	 * on the CAT�s medical record (MEDICAL).
	 * @throws SQLException
	 */
	public void isCatQualified() throws SQLException {
		//TODO
	}
}