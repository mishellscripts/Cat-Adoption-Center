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
    public CatDirectoryFrame(CatAdoptionModel model, HashMap<Integer,String> data) {
    	this.model = model;
    	initializeWindow();
    	show_cat_match_record(data);
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
    
    public void show_cat_match_record(HashMap<Integer,String> data) {
    	//TODO
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

            // Show add/remove/update buttons for medical record option only        
            if (option == 0) {
	    		JButton btnAddRecord = new JButton("Add");
	    		btnAddRecord.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				
	    			}
	    		});
	    		
	    		JButton btnRemoveRecord = new JButton("Remove");
	    		btnRemoveRecord.addActionListener(new ActionListener() {
	    			public void actionPerformed(ActionEvent e) {
	    				
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
            
            tablePanel.add(sp);
            mainPanel.add(tablePanel);
            mainPanel.add(buttonPanel);
           
            tableFrame.add(mainPanel);
            tableFrame.setSize(600, 500);
            tableFrame.setVisible(true);
        }
        
        
    }
}