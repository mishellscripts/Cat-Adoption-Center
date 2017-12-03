import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class LocationFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private CatAdoptionModel model;

	public LocationFrame(CatAdoptionModel catModel) {
		model = catModel;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCatAdoptionCenter = new JLabel("CAT ADOPTION CENTER");
		lblCatAdoptionCenter.setHorizontalAlignment(SwingConstants.CENTER);
		lblCatAdoptionCenter.setBounds(10, 22, 414, 26);
		lblCatAdoptionCenter.setFont(new Font("Calibri", Font.BOLD, 30));
		contentPane.add(lblCatAdoptionCenter);
		
		textField = new JTextField();
		textField.setBounds(86, 125, 137, 41);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Arial", Font.PLAIN, 11));
		btnSearch.setBounds(233, 125, 114, 41);
		contentPane.add(btnSearch);
		
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String location = textField.getText();
				try {
					int locID = model.searchAdoptionCenter(location);
					if (locID != 0) {
						model.setLocation(locID, location);
						WelcomeFrame welcomeFrame = new WelcomeFrame(model);
						welcomeFrame.setLocationRelativeTo(null);
						welcomeFrame.setVisible(true);
						dispose();	
					} else {
						JOptionPane.showMessageDialog(null, "Invalid location");	
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			}
		});
		
		JLabel lblLocation = new JLabel("Location:");
		lblLocation.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLocation.setBounds(86, 104, 67, 16);
		contentPane.add(lblLocation);
	}
}
