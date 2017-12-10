import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;

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
		
		JButton btnDonateACat = new JButton("Donate a Cat");
		btnDonateACat.setFont(new Font("Arial", Font.PLAIN, 12));
		btnDonateACat.setBounds(85, 180, 270, 32);
		btnDonateACat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterCatFrame registerCatFrame = new RegisterCatFrame(model, new UserFrame(model));
				registerCatFrame.setLocationRelativeTo(null);
				registerCatFrame.setVisible(true);
				dispose();
			}				
		});
		contentPane.add(btnDonateACat);
		
		JButton btnViewCatsIn = new JButton("View all cats");
		btnViewCatsIn.setFont(new Font("Arial", Font.PLAIN, 12));
		btnViewCatsIn.setBounds(85, 60, 270, 32);
		btnViewCatsIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CatDirectoryFrame cdf = new CatDirectoryFrame(model, new UserFrame(model), 3);
				dispose();
			}				
		});
		contentPane.add(btnViewCatsIn);
		
		JButton btnViewMedicalIn = new JButton("View medical records");
		btnViewMedicalIn.setFont(new Font("Arial", Font.PLAIN, 12));
		btnViewMedicalIn.setBounds(85, 100, 270, 32);
		btnViewMedicalIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CatDirectoryFrame cdf = new CatDirectoryFrame(model, new UserFrame(model), 2);
				dispose();
			}				
		});
		contentPane.add(btnViewMedicalIn);
		
		JButton btnReturnACat = new JButton("Return a Cat");
		btnReturnACat.setFont(new Font("Arial", Font.PLAIN, 12));
		btnReturnACat.setBounds(85, 140, 270, 32);
		btnReturnACat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnCatFrame rcf = new ReturnCatFrame(model);
				rcf.setLocationRelativeTo(null);
				dispose();
			}
		});
		contentPane.add(btnReturnACat);
		
		JButton btnFindMatchingCat = new JButton("Find Matching Cat");
		btnFindMatchingCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindMatchFrame fmf = new FindMatchFrame(model);
				fmf.setLocationRelativeTo(null);
				dispose();
			}
		});
		btnFindMatchingCat.setFont(new Font("Arial", Font.PLAIN, 12));
		btnFindMatchingCat.setBounds(85, 20, 270, 32);
		contentPane.add(btnFindMatchingCat);
		
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
