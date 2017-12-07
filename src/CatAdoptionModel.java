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

/**
 * CatAdoptionModel is responsible for establishing connection to the MySQL database
 * and contains location information specific to an adoption center and
 * all the functions to query and update the database.
 * @author CS157A Team CATS
 *
 */
public class CatAdoptionModel {
	private Connection connection;
	private String location;
	private int locID;
	
	static final String[] adoptionCols = {"aID", "pID", "cID", "adoption_date", "updatedAt"};
	static final String[] adoptionCenterCols = {"locID", "location"};
	static final String[] catCols = {"cID", "cName", "age", "gender", "breed", "adoption_fee", "locID", "adopted"};
	static final String[] medicalCols = {"cID", "disease", "medical_fee"};
	static final String[] personCols = {"pID","first_name","last_name","age","experience"};
	
	public CatAdoptionModel() {
		DataSource ds = DataSourceFactory.getMySQLDataSource();
	    try {
			connection = ds.getConnection();
			
			// Archive data that is a week old or more
			Long currentMs = System.currentTimeMillis();
			Long weekMs = Long.valueOf(7 * 24 * 60 * 60 * 1000);
			Timestamp cutoff = new Timestamp(currentMs - weekMs);
			System.out.println(cutoff.toString());
			
			CallableStatement cs = connection.prepareCall("{CALL archive_adoptions(?)}");
			cs.setTimestamp(1, cutoff);
			int test = cs.executeUpdate(); //TODO: take out the int test during finalization
			System.out.println("Affected rows (archived): " + test);
			
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	}
	
	/**
	 * Setter method to update the location of the model
	 * @param 	id 		the location id
	 * @param 	loc 	the location name
	 */
	public void setLocation(int id, String loc) {
		location = loc;
		locID = id;
	}
	
	/**
	 * Getter method to obtain the location id of the model
	 * @return 	int 	the location id
	 */	
	public int getLocationID() {
		return locID;
	}
	
	/**
	 * Getter method to obtain the location name of the model
	 * @return 	String	the location name
	 */
	public String getLocationName() {
		return location;
	}
	
	/**
	 * #1 PEOPLE can adopt a CAT from a ADOPTION_CENTER.
	 * @param 	firstName 	the first name of the user adopting the cat
	 * @param 	lastName 	the last name of the user adopting the cat
	 * @param 	age 		the age of the user adopting the cat
	 * @param 	experience  the experience level of the user adopting the cat
	 * 
	 */
	public void adoptCat(String firstName, String lastName, int age, String experience, int cID) throws SQLException {
		
		// Add user information to the db table Person
		Statement st = (Statement) connection.createStatement();
		String query = "INSERT INTO person(first_name, last_name, age, experience) VALUES "
				+ "('" + firstName + "', '" + lastName + "', '" + age + "', '" + experience + "')";
		st.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		
		// Get the id of the newly added person
		ResultSet rs = st.getGeneratedKeys();
		if (rs.next()) {
		    int pID = rs.getInt(1);
			
			// Add adoption record to the db table Cat
			CallableStatement cs = connection.prepareCall("{CALL adopt_cat(?,?)}");
			cs.setInt(1, pID);
			cs.setInt(2, cID);
			cs.execute();
		} else {
			System.out.println("Encountered an error with adding user to the database. Cannot proceed with adoption process.");
		}
	}
	
	/**
	 * #2 PEOPLE can donate their CAT to a ADOPTION_CENTER.
	 * @param 	name	the name of the cat being donated
	 * @param 	age		the age of the cat being donated
	 * @param 	gender	the gender of the cat being donated (M or F)
	 * @param 	breed	the breed of the cat being donated
	 * @param 	locID	the location of the adoption center the cat is donated to
	 * 
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
	 * If no adoption center is found in location, the result location ID is 0.
	 * @param 	location 	the location entered by user
	 * @return	int			the location id corresponding to the location searched
	 * 
	 */
	public int searchAdoptionCenter(String location) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_adoption_center(?)}");
		cs.setString(1, location);
		ResultSet rs = cs.executeQuery();
		int locID = 0;
		
		if (rs.next()) {
			locID = rs.getInt("locID"); 
		}
		return locID;
	}

	/**
	 * #4 PEOPLE can search for a CAT from a ADOPTION_CENTER they are currently visiting.
	 * @return	data containing all the cat tuples in a 2D ArrayList
	 */
	public ArrayList<ArrayList<String>> searchAdoptionCenterCats() throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_adoption_center_cats(?)}");
		cs.setInt(1, locID);
		ResultSet rs = cs.executeQuery();
		
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
            
            double adoptionFee = rs.getDouble(catCols[5]);
            rowList.add(String.valueOf(adoptionFee));
            
            double totalFee = calculateCosts(cID);            
            totalFee = Math.max(totalFee, adoptionFee);
            rowList.add(String.valueOf(totalFee));
           
            /*System.out.println("cID:" + cID + " Name:" + name + " Age:" + age + " Gender:" + gender + " Breed:" + breed + 
            		" Adoption Fee:" + adoptionFee + " Total Fee:" + totalFee);*/
            
            columnList.add(rowList);
        }
		
		return columnList;
	}

	/**
	 * #5 PEOPLE can view all CAT MEDICAL records from the ADOPTION_CENTER they are currently visiting
	 * @return	data containing all the cat medical records from current location tuples in a 2D ArrayList
	 */
	public ArrayList<ArrayList<String>> searchAdoptionCenterRecords() throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_adoption_center_records(?)}");
		cs.setInt(1, locID);
		ResultSet rs = cs.executeQuery();
		
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

            columnList.add(rowList);
        }

        return columnList;
	}

	/**
	 * #6 PEOPLE can return the CAT to the ADOPTION_CENTER they adopted from.
	 * @param 	id 		the id of the cat being returned to the adoption center
	 */
	public void returnCat(int id) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL return_cat(?)}");
		cs.setInt(1, id);
		ResultSet rs = cs.executeQuery();
	}

	/**
	 * #7 ADOPTION_CENTER can view all CATs and their MEDICAL records
	 * @return	data containing all the cat medical record tuples in a 2D ArrayList
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

            String fee = rs.getString(medicalCols[2]);
            rowList.add(fee);

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
			filters = filters + k + " = \"" + preferences.get(k) + "\"";
			if (i < preferences.keySet().size() - 1) {
				filters = filters + " AND ";
			}
		}
		String query = "SELECT cID, cName " //TODO: return more columns?
				+ "FROM cat WHERE " + filters;
		System.out.println(query);
		Statement st = (Statement) connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		while (rs.next()) {
			rowList.put(rs.getInt(catCols[0]), rs.getString(catCols[1]));
		}	
		return rowList;
	} 

	/**
	 * #9 ADOPTION_CENTER can register a found CAT.
	 * @param 	name	the name of the cat being registered
	 * @param 	age		the age of the cat being registered
	 * @param 	gender	the gender of the cat being registered (M or F)
	 * @param 	breed	the breed of the cat being registered
	 * @param	fee		the price of the cat being registered (base fee)
	 * @param 	locID	the location of the adoption center the cat is donated to
	 * 
	 */
	public void insertCat(String name, int age, String gender, String breed, double fee, int locID) throws SQLException {
		// referencing slide 32 of JDBC
		PreparedStatement ps = null;
		String query = "INSERT INTO cat(cName, age, gender, breed, adoption_fee, locID) VALUES (?,?,?,?,?,?)";
		ps = connection.prepareStatement(query);
		ps.setString(1, name);
		ps.setInt(2, age);
		ps.setString(3, gender.toUpperCase());
		ps.setString(4, breed);
		ps.setDouble(5, fee);
		ps.setInt(6, locID);
		int test = ps.executeUpdate(); //TODO: take out the int test during finalization
		System.out.println("Affected rows insertCat: " + test);
	}

	/**
	 * #10 ADOPTION_CENTER can remove a CAT's MEDICAL record. 
	 * @param 	cID			the id of the cat to remove
	 * @param	disease		the disease name to remove
	 * 
	 */
	public void removeMedical(int cID, String disease) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL remove_cat_medical_record(?, ?)}");
		cs.setInt(1, cID);
		cs.setString(2, disease);
		int test = cs.executeUpdate(); //TODO: take out the int test during finalization
		System.out.println("Affected rows removeMedical: " + test);
	}


	/**
	 * #11 ADOPTION_CENTER can register a CAT's MEDICAL record.
	 * @param 	cID			the id of the cat to add record to
	 * @param 	disease		the disease name to add 
	 * @param 	fee			the medical fee amount for the disease
	 * 
	 */
	public void addMedical(int cID, String disease, double fee) throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL register_cat_medical_record(?,?,?)}");
		cs.setInt(1, cID);
		cs.setString(2, disease);
		cs.setDouble(3, fee);
		int test = cs.executeUpdate(); //TODO: take out the int test during finalization
		System.out.println("Affected rows addMedical: " + test);
	}

	/**
	 * #12 ADOPTION_CENTER can update a CAT's MEDICAL record.
	 * @param 	cID			the id of the cat whose record will be updated
	 * @param 	disease		the disease of the cat that will be updated
	 * @param 	fee			the new fee amount to update to
	 * 
	 */
	public void updateMedical(int cID, String disease, double fee)  throws SQLException  {
		CallableStatement cs = connection.prepareCall("{CALL update_cat_medical_fee(?,?,?)}");
		cs.setInt(1,cID);
		cs.setString(2, disease);
		cs.setDouble(3, fee);
		int test = cs.executeUpdate(); //TODO: take out the int test during finalization
		System.out.println("Affected rows updateMedical: " + test);
	}

	/**
	 * #13 ADOPTION_CENTER can view all ADOPTION records.
	 * @return	data containing all the adoption records tuples in a 2D ArrayList
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
	 * #14 ADOPTION_CENTER can determine if the person (PEOPLE) is qualified to adopt
	 * a specific CAT based on person experience level.
	 * @param 	experience	the experience level of the person
	 * @param 	cID			the id of the cat in question
	 * @return 	boolean		the boolean result of whether the person is qualified or not	
	 *
	 */
	public boolean isPersonQualified(String experience, int cID) throws SQLException {
		String query = "";
		
		// Build the query depending on person's experience
		if (experience.equalsIgnoreCase("low")) {
			query = "Select cID, cName, age From cat c Where (age > 3) "
					+ "And cID = " + cID
					+ " And not exists (select * from medical m where m.cID=c.cID)";
		}
		else if (experience.equalsIgnoreCase("medium")) {
			query = "Select cID, cName, age From cat c Where (age > 1) "
					+ "And cID = " + cID
					+ " And not exists (select * from medical m where m.cID=c.cID)";
		}
		else {
			query = "Select cID, cName, age from cat";
		}
		
		Statement cs = (Statement) connection.createStatement();
		ResultSet rs = cs.executeQuery(query);
		
		return rs.isBeforeFirst(); 
	}

	/**
	 * #15 ADOPTION_CENTER can determine if the CAT is adoptable based
	 * on the CAT's medical record (MEDICAL).
	 * @return 	boolean		result indicating whether a cat is healthy enough to be adopted
	 * 
	 */
	public boolean isCatQualified(int cID) throws SQLException {		
		String query = "SELECT * FROM cat c WHERE c.cID = " + cID + 
				" AND c.cID NOT IN (SELECT cID FROM medical m WHERE cID=c.cID AND disease='Rabies'" +       
				" UNION SELECT cID FROM medical m WHERE cID=c.cID AND disease='Ringworm')";

		Statement st = (Statement) connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		
		return rs.next();
	}
	
	/**
	 * #16 ADOPTION_CENTER can calculate the cost of a CAT based on its adoption fee and medical fee. 
	 * @param	cID		the id of the cat whose total fee will be calculated
	 * @return 	double	sum of adoption and medical
	 * 
	 */
	public double calculateCosts(int cID) throws SQLException {
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery("SELECT sum(medical_fee) + adoption_fee "
				+ "FROM cat c, medical m WHERE c.cID=" + cID + " AND c.cID=m.cID GROUP BY c.cID");
		
		return rs.first() ? rs.getDouble(1) : 0; 
	}
	
	/**
	 * Allow user to see all their past adoptions
	 * @param uID
	 * @return
	 * @throws SQLException
	 */
	public HashMap<Integer, String> getUserAdoptions(int uID) throws SQLException {
		HashMap<Integer, String> hm =  new HashMap<Integer, String>();
		PreparedStatement ps = connection.prepareStatement("SELECT * FROM adoption a WHERE uID = ?");
		ps.setInt(1, uID);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			hm.put(rs.getInt(adoptionCols[2]), rs.getString(adoptionCols[3]));	
		}
		
		return hm;
	}
	/*
	public static void main(String[] args) throws SQLException {
		CatAdoptionModel cam = new CatAdoptionModel();
		HashMap<String, String> testPref = new HashMap<String, String>();
		testPref.put("age", "2");
		HashMap<Integer, String> test = cam.searchCatByPreference(testPref);
		System.out.println(test.keySet());
	}*/
}