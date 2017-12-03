import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sql.DataSource;

public class CatAdoptionModel {
	private Connection connection;
	private String location;
	private int locID;
	
	private String[] adoptionCols = {"aID", "pID", "cID", "adoption_date", "updatedAt"};
	private String[] adoptionCenterCols = {"locID", "location"};
	private String[] catCols = {"cID", "cName", "age", "gender", "breed", "adoption_fee", "locID", "adopted"};
	private String[] medicalCols = {"cID", "disease", "medical_fee"};
	private String[] personCols = {"pID","first_name","last_name","age","experience"};
	
	public CatAdoptionModel() {
		DataSource ds = DataSourceFactory.getMySQLDataSource();
	    try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}
	
	public void setLocation(int id, String loc) {
		//System.out.println("Setting location to " + id);
		location = loc;
		locID = id;
		//System.out.println(getLocation());
	}
	
	public int getLocationID() {
		return locID;
	}
	
	public String getLocationName() {
		return location;
	}
	
	/**
	 * #1 PEOPLE can adopt a CAT from a ADOPTION_CENTER.
	 * @param pID
	 * @param cID
	 * @throws SQLException 
	 */
	public void adoptCat(int pID, int cID) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL adopt_cat(?,?)}");
		cs.setInt(1,pID);
		cs.setInt(2, cID);
		cs.execute();
	}
	
	/**
	 * PEOPLE can donate their CAT to a ADOPTION_CENTER.
	 * @param name
	 * @param age
	 * @param gender
	 * @param breed
	 * @param locID
	 * @throws SQLException 
	 */
	public void donateCat(String name, int age, String gender, String breed, int locID) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL donate_cat(?,?,?,?,?)}");
		cs.setString(1, name);
		cs.setInt(2, age);
		cs.setString(3, gender);
		cs.setString(4, breed);
		cs.setInt(5, locID);
		cs.executeUpdate();
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
	 */
	public ArrayList<ArrayList<String>> searchAdoptionCenterCats() throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_adoption_center_cats(?)}");
		cs.setInt(1, locID);
		ResultSet rs = cs.executeQuery();
		
		ArrayList<ArrayList<String>> columnList = new ArrayList<ArrayList<String>>();
		
		while(rs.next()) {

            ArrayList<String> rowList = new ArrayList<>();

            int cID = rs.getInt("cID");
            rowList.add(String.valueOf(cID));

            String name = rs.getString("cName");
            rowList.add(name);

            int age = rs.getInt("age");
            rowList.add(String.valueOf(age));

            String gender = rs.getString("gender");
            rowList.add(gender);

            String breed = rs.getString("breed");
            rowList.add(breed);
            
            double adoptionFee = rs.getDouble("adoption_fee");
            rowList.add(String.valueOf(adoptionFee));

            int locID = rs.getInt("locID");
            rowList.add(String.valueOf(locID));
            
            int adopted = rs.getInt("adopted");
            rowList.add(String.valueOf(adopted));

            System.out.println("cID:" + cID + " Name:" + name + " Age:" + age + " Gender:" + gender + " Breed:" + breed + 
            		" Adoption Fee:" + adoptionFee + " locID:" + locID + " Adopted:" + adopted);

            columnList.add(rowList);
        }
		
		return columnList;
	}

	/**
	 * #5 PEOPLE can view all CAT MEDICAL records from the ADOPTION_CENTER they are currently visiting
	 * STORED PROCEDURE
	 */
	public ArrayList<ArrayList<String>> searchAdoptionCenterRecords() throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_adoption_center_records(?)}");
		cs.setInt(1, locID);
		ResultSet rs = cs.executeQuery();
		
		ArrayList<ArrayList<String>> columnList = new ArrayList<ArrayList<String>>();

        while(rs.next()) {

            ArrayList<String> rowList = new ArrayList<>();

            int cID = rs.getInt("cID");
            rowList.add(String.valueOf(cID));

            String name = rs.getString("cName");
            rowList.add(name);

            int age = rs.getInt("age");
            rowList.add(String.valueOf(age));

            String gender = rs.getString("gender");
            rowList.add(gender);

            String breed = rs.getString("breed");
            rowList.add(breed);
            
            String disease = rs.getString("disease");
            rowList.add(disease);

            columnList.add(rowList);
        }

        return columnList;
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
    public ArrayList<ArrayList<String>> searchCats() throws SQLException {
        Statement cs = (Statement) connection.createStatement();
        ResultSet rs = cs.executeQuery("CALL search_records()");

        ArrayList<ArrayList<String>> columnList = new ArrayList<ArrayList<String>>();

        while(rs.next()) {

            ArrayList<String> rowList = new ArrayList<>();

            int cID = rs.getInt(catCols[0]);
            rowList.add(String.valueOf(cID));

            String name = rs.getString(catCols[1]);
            rowList.add(name);

            int age = rs.getInt(catCols[2]);
            rowList.add(String.valueOf(age));

            String gender = rs.getString(catCols[3]);
            rowList.add(gender);

            String breed = rs.getString(catCols[4]);
            rowList.add(breed);

            String disease = rs.getString(medicalCols[1]);
            rowList.add(disease);

            //System.out.println("cID:" + cID + " Name:" + name + " Age:" + age + " Gender:" + gender + " Breed:" + breed + " Disease:" + disease);

            columnList.add(rowList);
        }

        return columnList;
    }

	/**
	 * #8 ADOPTION_CENTER can match PEOPLE to a CAT based on preference.
	 * @parameter hashmap from search filters. if user wants a female tabby, the hashmap
	 * 	sent here will look like (breed, tabby), (gender, female)
	 */
	public HashMap<Integer, String> searchCatByPreference(HashMap<String, String> preferences) throws SQLException {
		HashMap<Integer, String> rowList = new HashMap<Integer, String>();
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
		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			rowList.put(rs.getInt(catCols[0]), rs.getString(catCols[1]));
		}	
		return rowList;
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
	public void insertCat(String name, int age, String gender, String breed, double fee, int locID) throws SQLException {
		// referencing slide 32 of JDBC
		PreparedStatement ps = null;
		String query = "INSERT INTO cat(cName, age, gender, breed, adoption_fee, locID) VALUES (?,?,?,?,?,?)";
		ps = connection.prepareStatement(query);
		ps.setString(1, name);
		ps.setInt(2, age);
		ps.setString(3, gender);
		ps.setString(4, breed);
		ps.setDouble(5, fee);
		ps.setInt(6, locID);
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
	public ArrayList<ArrayList<String>> viewAllAdoptions() throws SQLException {		
		Statement cs = (Statement) connection.createStatement();
        ResultSet rs = cs.executeQuery("CALL view_all_adoptions()");

        ArrayList<ArrayList<String>> columnList = new ArrayList<ArrayList<String>>();

        while(rs.next()) {

            ArrayList<String> rowList = new ArrayList<>();

            int aID = rs.getInt(adoptionCols[0]);
            rowList.add(String.valueOf(aID));
            
            int pID = rs.getInt(adoptionCols[1]);
            rowList.add(String.valueOf(pID));
            
            int cID = rs.getInt(adoptionCols[2]);
            rowList.add(String.valueOf(cID));

            Timestamp adoptionDate = rs.getTimestamp(adoptionCols[3]);
            rowList.add(String.valueOf(adoptionDate));
            
            Timestamp updatedAt = rs.getTimestamp(adoptionCols[4]);
            rowList.add(String.valueOf(updatedAt));

            columnList.add(rowList);
        }

        return columnList;
	}

	/**
	 * #15 ADOPTION_CENTER can determine if the person (PEOPLE) is qualified to adopt
	 * a specific CAT based on person experience level.
	 * @param experience
	 * @return whether there are rows in the result set
	 * @throws SQLException
	 */
	public boolean isPersonQualified(String experience, int cID) throws SQLException {
		String query = "";
		if (experience.equalsIgnoreCase("low")) {
			query = "Select cID, name, age From Cat c Where (age > 3) "
					+ "And cID = ? "
					+ "And not exists (select * from Medical m where m.cID=c.cID)";
		}
		else if (experience.equalsIgnoreCase("medium")) {
			query = "Select cID, name, age From Cat c Where (age > 1) "
					+ "And cID = ? "
					+ "And not exists (select * from Medical m where m.cID=c.cID)";
		}
		else {
			query = "Select cID, name, age from Cat";
		}
		
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, cID);
		ResultSet rs = ps.executeQuery(query);
		
		return rs.isBeforeFirst(); 
	}

	/**
	 * #16 ADOPTION_CENTER can determine if the CAT is adoptable based
	 * on the CAT�s medical record (MEDICAL).
	 * @return if there are rows in this result set
	 * @throws SQLException
	 */
	public boolean isCatQualified(int cID) throws SQLException {
		String query = "Select * from Cat c Where cID = ?"
				+ "And cID in (select * from Medical where cID = c.cID "
				+ "and disease<>'Rabies' and disease<>'Ringworm') "
				+ "Or cID not in (select * from Medical where cID = c.cID)";
		PreparedStatement ps = connection.prepareStatement(query);
		ps.setInt(1, cID);
		ResultSet rs = ps.executeQuery();
		
		return rs.isBeforeFirst();
	}
	
	/**
	 * ADOPTION_CENTER can calculate the cost of a CAT based on its adoption fee and medical fee. 
	 * @return sum of adoption and medical
	 * @throws SQLException
	 */
	public int calculateCosts() throws SQLException {
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("SELECT sum(medical_fee) + adoption_fee "
				+ "FROM cat c, medical m WHERE c.cID=m.cID GROUP BY c.cID");
		return rs.getInt(0); //TODO: is this the zeroth column
	}
}