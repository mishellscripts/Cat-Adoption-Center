import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;

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
		
		JButton btnViewAllMedical = new JButton("Manage medical records");
		btnViewAllMedical.setFont(new Font("Arial", Font.PLAIN, 12));
		btnViewAllMedical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CatDirectoryFrame adminFrame = new CatDirectoryFrame(model, new AdminFrame(model), 0);
				dispose();
			}
		});
		
		btnViewAllMedical.setBounds(90, 30, 267, 37);
		contentPane.add(btnViewAllMedical);
		
		JButton btnRegisterCat = new JButton("Register Cat");
		btnRegisterCat.setFont(new Font("Arial", Font.PLAIN, 12));
		btnRegisterCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterCatFrame registerCatFrame = new RegisterCatFrame(model, new AdminFrame(model));
				registerCatFrame.setLocationRelativeTo(null);
				registerCatFrame.setVisible(true);
				dispose();
			}
		});
		
		btnRegisterCat.setBounds(90, 78, 267, 37);
		contentPane.add(btnRegisterCat);
		
		JButton btnViewAllAdoption = new JButton("View adoption records");
		btnViewAllAdoption.setFont(new Font("Arial", Font.PLAIN, 12));
		btnViewAllAdoption.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CatDirectoryFrame adminFrame = new CatDirectoryFrame(model, new AdminFrame(model), 1);
				dispose();
			}
		});
		btnViewAllAdoption.setBounds(90, 126, 267, 37);
		contentPane.add(btnViewAllAdoption);
		
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
	}
}
