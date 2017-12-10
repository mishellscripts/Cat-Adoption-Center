import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.annotation.XmlType;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class CatDirectoryFrame extends JFrame {

    private CatAdoptionModel model;

    // to display cat preference search
    public CatDirectoryFrame(CatAdoptionModel model, HashMap<String,String> preferences) {
    	this.model = model;
    	show_cat_match_record(preferences);
    }
    
    // to display return cat; takes uID 
    public CatDirectoryFrame(CatAdoptionModel model, String uID) {
    	this.model = model;
    }
    
    public CatDirectoryFrame(CatAdoptionModel model, int option)
    {
        this.model = model;

        switch (option) {
            case 0:
                show_cat_and_medial_record();
                break;
            case 1:
            	show_cat_and_adoption_record();
            	break;
            case 2:
            	show_cat_and_medial_record_loc();
            	break;
            case 3:
            	show_cats_only();
            	break;
            default:
                System.out.print("Option not yet implemented");
        }
    }

    public void show_person_adoption_record(String uID) {
    	
    	String column[] = {"aID", "cID", "NAME", "DATE"};
    	
    	try {
			ArrayList<ArrayList<String>> results = model.getUserAdoptions(Integer.parseInt(uID));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void show_cat_match_record(HashMap<String, String> data) {
    	
    	String column[] = {"cID", "NAME"};
    	
    	try {
    		ArrayList<ArrayList<String>> hm = model.searchCatByPreference(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    
    public void show_cat_and_medial_record()
    {
        try
        {
            ArrayList<ArrayList<String>> columnList = model.searchCats();
            
            String column[] = {"ID", "NAME", "AGE", "GENDER", "BREED", "MEDICAL ISSUE", "MEDICAL FEE"};
            
            new ShowCatRecordTable(get_data_from_table(columnList, 7), column, 0);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void show_cat_and_adoption_record() 
    {
        try
        {
            ArrayList<ArrayList<String>> columnList = model.viewAllAdoptions();

            String column[] = {"ADOPTION ID", "PERSON ID", "CAT ID", "DATE", "UPDATED AT"};
            
            new ShowCatRecordTable(get_data_from_table(columnList, 5), column, 1);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
    
    public void show_cat_and_medial_record_loc()
    {
        try
        {
            ArrayList<ArrayList<String>> columnList = model.searchAdoptionCenterRecords();
            
            String column[] = {"ID", "NAME", "AGE", "GENDER", "BREED", "DISEASE"};
            
            new ShowCatRecordTable(get_data_from_table(columnList, 6), column, 2);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }    	
    }
    
    public void show_cats_only()
    {
    	try
        {
            ArrayList<ArrayList<String>> columnList = model.searchAdoptionCenterCats();

            String column[] = {"ID", "NAME", "AGE", "GENDER", "BREED", "ADOPTION FEE", "TOTAL FEE"};
            
            new ShowCatRecordTable(get_data_from_table(columnList, 7), column, 3);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }	
    }
    
    public String[][] get_data_from_table(ArrayList<ArrayList<String>> tuples, int numColumns) {
    	String data[][] = new String[tuples.size()][numColumns];
    	
        for (int col = 0; col < tuples.size(); col++)
        {
            for (int row = 0; row < numColumns; row++)
            {
                String item = tuples.get(col).get(row);

                if (item != null)
                {
                    data[col][row] = item;
                }
                else
                {
                    data[col][row] = "Healthy";
                }

            }
        }
        return data;
    }

    class ShowCatRecordTable
    {
        JFrame tableFrame;
        JPanel mainPanel;
        JPanel tablePanel;
        JPanel buttonPanel;
        
        JFrame formFrame;
        JPanel formPanel;

        public ShowCatRecordTable(String[][] data, String column[], int option)
        {
            tableFrame = new JFrame();
            mainPanel = new JPanel();
            tablePanel = new JPanel();
            buttonPanel = new JPanel();
            
            formFrame = new JFrame();
            formPanel = new JPanel();
            formPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
            formPanel.setLayout(null);
            formPanel.setBounds(100, 100, 450, 250);
            formFrame.setSize(500,300);
            formFrame.setLocationRelativeTo(null);
            formFrame.add(formPanel);

            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

            JTable jt = new JTable(data, column);
            jt.setBounds(50, 50, 550, 550);
            JScrollPane sp = new JScrollPane(jt);

            // Show add/remove/update buttons for admin medical record option only        
            if (option == 0) {
                JButton btnAddRecord = new JButton("Add");
                btnAddRecord.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try
                        {
                            int catIDColumn = 0;
                            int diseaseColumnIndex = 5;
                            int row = jt.getSelectedRow();
                            String cID = jt.getModel().getValueAt(row, catIDColumn).toString();
                            String recordedDisease = jt.getModel().getValueAt(row, diseaseColumnIndex).toString();

                            //tableFrame.dispose();
                            
                            JTextField diseaseField;
                            JTextField feeField;

                            JLabel lblDisease = new JLabel("Disease:");
                            lblDisease.setBounds(30, 30, 56, 16);
                            formPanel.add(lblDisease);

                            JLabel lblFee = new JLabel("Medical Fee:");
                            lblFee.setBounds(30, 70, 100, 16);
                            formPanel.add(lblFee);

                            diseaseField = new JTextField();
                            diseaseField.setBounds(150, 32, 100, 25);
                            formPanel.add(diseaseField);
                            diseaseField.setColumns(10);

                            feeField = new JTextField();
                            feeField.setBounds(150, 72, 100, 25);
                            formPanel.add(feeField);
                            feeField.setColumns(10);                          

                            JButton btnRegister = new JButton("Add Illness for Cat " + cID);

                            btnRegister.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {

                                    try
                                    {
                                        String disease = diseaseField.getText();
                                        double fee = Double.parseDouble(feeField.getText());

                                        if (!disease.equals(recordedDisease))
                                        {
                                            tableFrame.dispose();
                                            formFrame.dispose();

                                            model.addMedical(Integer.parseInt(cID), disease, fee);
                                            JOptionPane.showMessageDialog(null, "Cat " + cID
                                                    + " is registered with an Illness");

                                            AdminFrame adminFrame = new AdminFrame(model);
                                            adminFrame.setLocationRelativeTo(null);
                                            adminFrame.setVisible(true);
                                        }
                                        else
                                        {
                                            JOptionPane.showMessageDialog(null, "This cat " + cID
                                                    + " already has this illness, you cannot give it the same illness");
                                        }
                                    }
                                    catch (Exception error)
                                    {
                                        System.out.println("Insertion failed: make sure your entering a " +
                                                "disease in text format and Medical Fee in money format");
                                        error.printStackTrace();
                                    }
                                }
                            });

                            btnRegister.setBounds(150, 100, 200, 25);
                            formPanel.add(btnRegister);
                            
                            formFrame.setVisible(true);
                        }
                        catch (ArrayIndexOutOfBoundsException error)
                        {
                            JOptionPane.showMessageDialog(null, "Error: Please select a " +
                                    "cat from table first");
                        }
                    }
                });

                JButton btnRemoveRecord = new JButton("Remove");
                btnRemoveRecord.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try
                        {
                            int catIDColumnIndex = 0;
                            int diseaseColumnIndex = 5;
                            int row = jt.getSelectedRow();
                            String cID = jt.getModel().getValueAt(row, catIDColumnIndex).toString();
                            String disease = jt.getModel().getValueAt(row, diseaseColumnIndex).toString();

                            if (!disease.equals("Healthy"))
                            {
                                tableFrame.dispose();

                                try
                                {
                                    model.removeMedical(Integer.parseInt(cID), disease);
                                    JOptionPane.showMessageDialog(null, "Successfully removed " +
                                            "illness from cat " + cID);
                                }
                                catch (SQLException SQLError)
                                {
                                    JOptionPane.showMessageDialog(null, "Removal Error");
                                }

                                AdminFrame adminFrame = new AdminFrame(model);
                                adminFrame.setLocationRelativeTo(null);
                                adminFrame.setVisible(true);
                                dispose();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "This cat " + cID
                                        + " is Healthy, there is no medical illness to remove");
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException error)
                        {
                            JOptionPane.showMessageDialog(null, "Error: Please select a cat " +
                                    "from table first");
                        }
                    }
                });

                JButton btnUpdateRecord = new JButton("Update");
                btnUpdateRecord.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try
                        {
                            int catIDColumn = 0;
                            int diseaseColumnIndex = 5;
                            int feeColumnIndex = 6;
                            int row = jt.getSelectedRow();
                            String cID = jt.getModel().getValueAt(row, catIDColumn).toString();
                            String disease = jt.getModel().getValueAt(row, diseaseColumnIndex).toString();
                            String recordedFee = jt.getModel().getValueAt(row, feeColumnIndex).toString();

                            if (!disease.equals("Healthy"))
                            {
                                JTextField feeField;

                                //tableFrame.dispose();

                                JLabel lblFee = new JLabel("Medical Fee:");
                                lblFee.setBounds(30, 30, 100, 16);
                                formPanel.add(lblFee);

                                feeField = new JTextField();
                                feeField.setBounds(150, 32, 100, 25);
                                formPanel.add(feeField);
                                feeField.setColumns(10);

                                JButton btnUpdateFee = new JButton("Update medical fee for Cat " + cID);

                                btnUpdateFee.addActionListener(new ActionListener() {
                                    public void actionPerformed(ActionEvent e) {

                                        try
                                        {
                                            double fee = Double.parseDouble(feeField.getText());

                                            if (fee != Double.parseDouble(recordedFee))
                                            {
                                                model.updateMedical(Integer.parseInt(cID), disease, fee);
                                                JOptionPane.showMessageDialog(null, "Cat " + cID
                                                        + " updated with new medical fee for " + disease);
                                                
                                                tableFrame.dispose();
                                                formFrame.dispose();
                                                
                                                AdminFrame adminFrame = new AdminFrame(model);
                                                adminFrame.setLocationRelativeTo(null);
                                                adminFrame.setVisible(true);
                                            }
                                            else
                                            {
                                                JOptionPane.showMessageDialog(null, "This cat " + cID
                                                        + " already has this amount as a fee, you cannot give it the same fee");
                                            }
                                        }
                                        catch (Exception error)
                                        {
                                            System.out.println("Insertion failed: make sure your entering a " +
                                                    "Medical Fee in money format");
                                            error.printStackTrace();
                                        }
                                    }
                                });

                                btnUpdateFee.setBounds(150, 100, 250, 25);
                                formPanel.add(btnUpdateFee);
                                
                                formFrame.setVisible(true);
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Error: You cannot update a " +
                                        "medical fee to a cat that has no medical issues");
                            }

                        }
                        catch (ArrayIndexOutOfBoundsException error)
                        {
                            JOptionPane.showMessageDialog(null, "Error: Please select a " +
                                    "cat from table first");
                        }
                    }
                });

                buttonPanel.add(btnAddRecord);
                buttonPanel.add(btnRemoveRecord);
                buttonPanel.add(btnUpdateRecord);
            }             
            // Show adopt button for view all cats frame only
            else if (option == 3) {
            	JButton btnAdopt = new JButton("Adopt");
            	btnAdopt.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				// Get selected cat record
	    				int row = jt.getSelectedRow();
	    				if (row >= 0) {
	    					//System.out.println("Selected cID: " + jt.getValueAt(row, 0));
	    					
	    					int catID = Integer.parseInt( (String) jt.getValueAt(row, 0) );
	    					String catName = (String) jt.getValueAt(row, 1);
	    					
	    					try {
	    						
	    						// Display form only if the cat is qualified for adoption
								if (!model.isCatQualified(catID)) {
									JOptionPane.showMessageDialog(null, catName + " is currently not healthy enough for adoption.");
								} else {
			    					AdoptFrame adoptFrame = new AdoptFrame(model, catID, catName, 
			    					Double.parseDouble( (String) jt.getValueAt(row, 6) ));
			    					adoptFrame.setLocationRelativeTo(null);
			    					adoptFrame.setVisible(true);
			    					tableFrame.dispose();
								}
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
		    			}
	    			}
	    		});
            	buttonPanel.add(btnAdopt);
        	}
            
            tablePanel.add(sp);
            mainPanel.add(tablePanel);
            mainPanel.add(buttonPanel);
           
            tableFrame.add(mainPanel);
            tableFrame.setSize(600, 600);
            tableFrame.setLocationRelativeTo(null);
            tableFrame.setVisible(true);
        }
        
        
    }
}