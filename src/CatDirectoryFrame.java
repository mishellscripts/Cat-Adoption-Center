import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.annotation.XmlType;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CatDirectoryFrame extends JFrame {

	private JPanel contentPane;
    private CatAdoptionModel model;

    public CatDirectoryFrame(CatAdoptionModel model, int option)
    {
        this.model = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
		JButton btnPrevious = new JButton("< Previous");
		btnPrevious.setFont(new Font("Arial", Font.PLAIN, 11));
		btnPrevious.setBounds(10, 227, 89, 23);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WelcomeFrame wFrame = new WelcomeFrame(model);
				wFrame.setLocationRelativeTo(null);
				wFrame.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnPrevious);

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
                    data[col][row] = "No Data Available";
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

            // Show add/remove/update buttons for admin medical record option only        
            if (option == 0) {
	    		JButton btnAddRecord = new JButton("Add");
	    		btnAddRecord.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				
	    			}
	    		});
	    		
	    		JButton btnRemoveRecord = new JButton("Remove");
	    		btnRemoveRecord.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				// Get selected medical record
	    				int row = jt.getSelectedRow();
	    				if (row >= 0) {
	    					System.out.println(jt.getValueAt(row, 0));
	    					System.out.println(jt.getValueAt(row, 5));
	    				}
	    			}
	    		});
	    		
	    		JButton btnUpdateRecord = new JButton("Update");
	    		btnUpdateRecord.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				// Get selected medical record
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
	    					AdoptFrame adoptFrame = new AdoptFrame(model, Integer.parseInt( (String) jt.getValueAt(row, 0) ),
	    							(String) jt.getValueAt(row, 1), Double.parseDouble( (String) jt.getValueAt(row, 6) ));
	    					adoptFrame.setLocationRelativeTo(null);
	    					adoptFrame.setVisible(true);
	    					dispose();
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
            tableFrame.setVisible(true);
        }
    }
}