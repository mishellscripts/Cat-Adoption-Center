import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CatAdoptionModel {
	private Connection connection;
	
	private JPanel currentPanel; // the current screen
	private ArrayList<ChangeListener> listeners;
	
	public CatAdoptionModel() {
		
		listeners = new ArrayList<>();
		
		DataSource ds = DataSourceFactory.getMySQLDataSource();
		
	    try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}
	    
	}
	
	public JPanel getPanel() {
		return currentPanel;
	}

	public void attach(ChangeListener c) {
		listeners.add(c);
	}
	
	public void update(JPanel newPanel) {
		currentPanel = newPanel;
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	public void searchCats() throws SQLException {
		CallableStatement cs = connection.prepareCall("{CALL search_records()}");
		ResultSet rs = cs.executeQuery();
		while(rs.next()) {
			int cID = rs.getInt("cID"); 
			String name = rs.getString("name"); 
			int age = rs.getInt("age");
			String gender = rs.getString("gender");
			String breed = rs.getString("breed");
			String disease = rs.getString("disease");
			System.out.println("cID:" + cID + " Name:" + name + " Age:" + age
					+ " Gender:" + gender + " Breed:" + breed + " Disease:" + disease); 
		}
	}
}