import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class UserFrame extends JFrame {

	private JPanel contentPane;
	private CatAdoptionModel model;
	
	public UserFrame(CatAdoptionModel catModel) {
		model = catModel;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAdoptACat = new JButton("Adopt a Cat");
		btnAdoptACat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdoptFrame adoptFrame = new AdoptFrame(model);
				adoptFrame.setVisible(true);
				dispose();
			}	
		});
		btnAdoptACat.setBounds(168, 42, 118, 25);
		contentPane.add(btnAdoptACat);
	}
}
