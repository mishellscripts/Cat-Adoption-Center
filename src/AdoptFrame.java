import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdoptFrame extends JFrame {

	private JPanel contentPane;
	private JTextField lastNameField;
	private JTextField firstNameField;
	private JTextField ageField;
	private JTextField expField;
	
	private CatAdoptionModel model;
	private int catID;
	private String catName;
	private double catFee;
	
	public AdoptFrame(CatAdoptionModel catModel, int id, String name, double fee) {
		model = catModel;
		catID = id;
		catName = name;
		catFee = fee;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFirstName.setBounds(30, 30, 73, 25);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Arial", Font.PLAIN, 13));
		lblLastName.setBounds(30, 68, 83, 16);
		contentPane.add(lblLastName);
		
		JLabel lblExperience = new JLabel("Experience:");
		lblExperience.setFont(new Font("Arial", Font.PLAIN, 13));
		lblExperience.setBounds(30, 135, 73, 16);
		contentPane.add(lblExperience);
		
		lastNameField = new JTextField();
		lastNameField.setBounds(103, 65, 116, 25);
		contentPane.add(lastNameField);
		lastNameField.setColumns(10);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setFont(new Font("Arial", Font.PLAIN, 13));
		lblAge.setBounds(67, 100, 27, 16);
		contentPane.add(lblAge);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setFont(new Font("Arial", Font.PLAIN, 12));
		btnSubmit.setBounds(323, 225, 97, 25);
		contentPane.add(btnSubmit);
		
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
					// Check if user is qualified
					if (!model.isPersonQualified(expField.getText(), catID)) {
						JOptionPane.showMessageDialog(null, "You are not qualified to adopt " + catName + " based on your experience level.");
						CatDirectoryFrame cdf = new CatDirectoryFrame(model, 3);
						dispose();
					}
					// Complete adoption
					else {
						/* This is my attempt to convert the double into a readable USD format
	    				NumberFormat fmt = NumberFormat.getCurrencyInstance();
	    				String confirmationMessage = "Confirm adoption for " + jt.getValueAt(row, 1) 
	    					+ " with fee of " + fmt.format(jt.getValueAt(row, 6)) + "?";*/
	    				
						// Display confirmation dialog
	    				String confirmationMessage = "Confirm adoption for " + catName 
	    						+ " with fee of " + catFee + "?";

	    				int option = JOptionPane.showConfirmDialog(null, confirmationMessage, "Are you sure?", JOptionPane.YES_NO_OPTION);
	    				
	    				if (option == 0) {
	    					// Add user to the db
	    					model.adoptCat(firstNameField.getText(), lastNameField.getText(), 
	    							Integer.parseInt(ageField.getText()), expField.getText(), catID);
	    					
	    					JOptionPane.showMessageDialog(null, "You have adopted " + catName + "!");
	    					UserFrame userFrame = new UserFrame(model);
	    					userFrame.setLocationRelativeTo(null);
	    					userFrame.setVisible(true);
	    					dispose();
	    				}
					}	
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		
		firstNameField = new JTextField();
		firstNameField.setColumns(10);
		firstNameField.setBounds(103, 30, 116, 25);
		contentPane.add(firstNameField);
		
		ageField = new JTextField();
		ageField.setColumns(10);
		ageField.setBounds(103, 97, 116, 25);
		contentPane.add(ageField);
		
		expField = new JTextField();
		expField.setColumns(10);
		expField.setBounds(103, 132, 116, 25);
		contentPane.add(expField);
		
		JButton btnPrevious = new JButton("< Previous");
		btnPrevious.setFont(new Font("Arial", Font.PLAIN, 12));
		btnPrevious.setBounds(10, 227, 103, 23);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CatDirectoryFrame cdf = new CatDirectoryFrame(model, 3);
				cdf.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnPrevious);
	}
}
