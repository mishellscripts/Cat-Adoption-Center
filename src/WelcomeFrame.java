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
import javax.swing.SwingConstants;

public class WelcomeFrame extends JFrame {

	private JPanel contentPane;
	private CatAdoptionModel model;

	public WelcomeFrame(CatAdoptionModel catModel) {
		model = catModel;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAdoptionCenter = new JLabel(model.getLocationName().toUpperCase() + " ADOPTION CENTER");
		lblAdoptionCenter.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdoptionCenter.setFont(new Font("Calibri", Font.BOLD, 24));
		lblAdoptionCenter.setBounds(10, 22, 414, 37);
		contentPane.add(lblAdoptionCenter);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.setFont(new Font("Arial", Font.PLAIN, 14));
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminFrame adminFrame = new AdminFrame(model);
				adminFrame.setLocationRelativeTo(null);
				adminFrame.setVisible(true);
				dispose();
			}
		});
		btnAdmin.setBounds(43, 88, 152, 93);
		contentPane.add(btnAdmin);
		
		JButton btnUser = new JButton("User");
		btnUser.setFont(new Font("Arial", Font.PLAIN, 14));
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserFrame userFrame = new UserFrame(model);
				userFrame.setLocationRelativeTo(null);
				userFrame.setVisible(true);
				dispose();
			}
		});
		btnUser.setBounds(231, 88, 152, 93);
		contentPane.add(btnUser);
		
		JButton btnPrevious = new JButton("< Previous");
		btnPrevious.setFont(new Font("Arial", Font.PLAIN, 11));
		btnPrevious.setBounds(10, 227, 89, 23);
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocationFrame locFrame = new LocationFrame(model);
				locFrame.setLocationRelativeTo(null);
				locFrame.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnPrevious);
	}

}
