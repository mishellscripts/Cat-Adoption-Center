import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class WelcomeFrame extends JFrame {

	private JPanel contentPane;
	private CatAdoptionModel model;

	public WelcomeFrame(String location) {
		System.out.println(model.getLocation());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAdoptionCenter = new JLabel(location.toUpperCase() + " ADOPTION CENTER");
		lblAdoptionCenter.setFont(new Font("Franklin Gothic Book", Font.BOLD, 24));
		lblAdoptionCenter.setBounds(34, 13, 360, 37);
		contentPane.add(lblAdoptionCenter);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminFrame adminFrame = new AdminFrame();
				adminFrame.setVisible(true);
				dispose();
			}
		});
		btnAdmin.setBounds(43, 88, 152, 93);
		contentPane.add(btnAdmin);
		
		JButton btnUser = new JButton("User");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserFrame userFrame = new UserFrame();
				userFrame.setVisible(true);
				dispose();
			}
		});
		btnUser.setBounds(231, 88, 152, 93);
		contentPane.add(btnUser);
	}

}
