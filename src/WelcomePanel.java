import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JTextField;

public class WelcomePanel extends JPanel{
	public WelcomePanel(CatAdoptionModel model) {
		setLayout(null);
		
		JLabel lblMountainview = new JLabel("MOUNTAINVIEW");
		lblMountainview.setFont(new Font("Verdana", Font.PLAIN, 24));
		lblMountainview.setBounds(20, 11, 204, 41);
		add(lblMountainview);
		
		JButton btnUser = new JButton("User");
		btnUser.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnUser.setBounds(253, 110, 145, 81);
		add(btnUser);
		
		JButton btnAdmin = new JButton("Admin");
		btnAdmin.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		btnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdmin.setBounds(48, 109, 145, 82);
		add(btnAdmin);
	}
}
