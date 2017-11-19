import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private CatAdoptionModel model;

	public AdminFrame(CatAdoptionModel catModel) {
		model = catModel;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnViewAllMedical = new JButton("View all medical records");
		btnViewAllMedical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.searchCats();
				} catch (SQLException s) {
					System.out.println("Sad: " + s.getMessage());
				}
			}
		});
		
		btnViewAllMedical.setBounds(124, 30, 188, 37);
		contentPane.add(btnViewAllMedical);
		
		JButton btnRegisterCat = new JButton("Register Cat");
		btnRegisterCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdoptFrame adoptFrame = new AdoptFrame(model);
				adoptFrame.setVisible(true);
				dispose();
			}
		});
		
		btnRegisterCat.setBounds(124, 88, 188, 30);
		contentPane.add(btnRegisterCat);
	}

}
