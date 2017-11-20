import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

        try
        {
            model.searchCats();
        }
        catch (Exception e)
        {
            System.out.println("Search failed.");
            e.printStackTrace();
        }
    }

}