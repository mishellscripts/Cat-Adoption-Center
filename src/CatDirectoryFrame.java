import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.annotation.XmlType;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class CatDirectoryFrame extends JFrame {

	private JPanel contentPane;
    private CatAdoptionModel model;

    //to display cat preference search
    public CatDirectoryFrame(CatAdoptionModel model, HashMap<String,String> preferences) {
    	this.model = model;
    	initializeWindow();
    	show_cat_match_record(preferences);
    }
    
    public CatDirectoryFrame(CatAdoptionModel model, int option)
    {
        this.model = model;

        initializeWindow();

        switch (option) {
            case 0:
                show_cat_and_medial_record();
                break;
            case 1:
            	show_cat_and_adoption_record();
            	break;
            default:
                System.out.print("Option not yet implemented");
        }
    }

    public void initializeWindow() {
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
    }
    
    public void show_cat_match_record(HashMap<String,String> data) {
    	try {
			HashMap<Integer, String> hm = model.searchCatByPreference(data);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	String column[] = {"ID", "NAME"};
    }
    
    public void show_cat_and_medial_record()
    {
        try
        {
            ArrayList<ArrayList<String>> columnList = model.searchCats();
            
            String column[] = {"ID", "NAME", "AGE", "GENDER", "BREED", "DISEASE"};
            
            new ShowCatRecordTable(get_data_from_table(columnList, 6), column, 0);
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

        public ShowCatRecordTable(String[][] data, String column[], int option)
        {
            tableFrame = new JFrame();
            mainPanel = new JPanel();
            tablePanel = new JPanel();
            buttonPanel = new JPanel();
            
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

            JTable jt = new JTable(data, column);
            jt.setBounds(50, 50, 400, 400);
            JScrollPane sp = new JScrollPane(jt);

            // Show add/remove/update buttons for medical record option only        
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

                            JPanel contentPane;
                            JTextField diseaseField;
                            JTextField feeField;

                            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            setBounds(100, 100, 450, 250);
                            contentPane = new JPanel();
                            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                            setContentPane(contentPane);
                            contentPane.setLayout(null);

                            JLabel lblDisease = new JLabel("Disease:");
                            lblDisease.setBounds(30, 30, 56, 16);
                            contentPane.add(lblDisease);

                            JLabel lblFee = new JLabel("Medical Fee:");
                            lblFee.setBounds(30, 70, 100, 16);
                            contentPane.add(lblFee);

                            diseaseField = new JTextField();
                            diseaseField.setBounds(150, 32, 100, 25);
                            contentPane.add(diseaseField);
                            diseaseField.setColumns(10);

                            feeField = new JTextField();
                            feeField.setBounds(150, 72, 100, 25);
                            contentPane.add(feeField);
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
                                            model.addMedical(Integer.parseInt(cID), disease, fee);
                                            JOptionPane.showMessageDialog(null, "Cat " + cID
                                                    + " is registered with an Illness");

                                            AdminFrame adminFrame = new AdminFrame(model);
                                            adminFrame.setVisible(true);
                                            dispose();
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

                            btnRegister.setBounds(200, 200, 200, 25);
                            contentPane.add(btnRegister);
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
                                try
                                {
                                    model.removeMedical(Integer.getInteger(cID), disease);
                                    JOptionPane.showMessageDialog(null, "Successfully removed " +
                                            "illness from cat " + cID);
                                }
                                catch (SQLException SQLError)
                                {
                                    JOptionPane.showMessageDialog(null, "Error: SQL Exception");
                                }

                                AdminFrame adminFrame = new AdminFrame(model);
                                adminFrame.setVisible(true);
                                dispose();
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "This cat " + cID
                                        + " is Healthy, there is no medical illness to remove");
                                AdminFrame adminFrame = new AdminFrame(model);
                                adminFrame.setVisible(true);
                                dispose();
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
                            int row = jt.getSelectedRow();
                            String value = jt.getModel().getValueAt(row, catIDColumn).toString();
                            System.out.println("Update Medical Issue to cat: " + value);
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
            
            tablePanel.add(sp);
            mainPanel.add(tablePanel);
            mainPanel.add(buttonPanel);
           
            tableFrame.add(mainPanel);
            tableFrame.setSize(600, 500);
            tableFrame.setVisible(true);
        }
        
        
    }
}