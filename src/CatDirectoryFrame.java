import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.annotation.XmlType;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CatDirectoryFrame extends JFrame {

    private JPanel contentPane;
    private CatAdoptionModel model;

    /**
     * Create the frame.
     */
    public CatDirectoryFrame(CatAdoptionModel model, int option)
    {
        this.model = model;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        switch (option) {
            case 0:
                show_cat_and_medial_record();

            default:
                System.out.print("Option not yet implemented");
        }
    }

    public void show_cat_and_medial_record()
    {
        try
        {
            ArrayList<ArrayList<String>> columnList = model.searchCats();

            String data[][] = new String[columnList.size()][6];

            for (int col = 0; col < columnList.size(); col++)
            {
                for (int row = 0; row < 6; row++)
                {
                    String item = columnList.get(col).get(row);

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

            new ShowCatMedialRecordTable(data);
        }
        catch (Exception e)
        {
            System.out.println("Search failed.");
            e.printStackTrace();
        }
    }

    class ShowCatMedialRecordTable
    {
        JFrame tableFrame;

        public ShowCatMedialRecordTable(String[][] data)
        {
            tableFrame = new JFrame();

            String column[] = {"ID", "NAME", "AGE", "GENDER", "BREED", "DISEASE"};

            JTable jt = new JTable(data, column);
            jt.setBounds(50, 50, 400, 400);
            JScrollPane sp = new JScrollPane(jt);

            tableFrame.add(sp);
            tableFrame.setSize(600, 500);
            tableFrame.setVisible(true);
        }
    }
}