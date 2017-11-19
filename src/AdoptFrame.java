import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AdoptFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private CatAdoptionModel model;
	
	public AdoptFrame(CatAdoptionModel catModel) {
		model = catModel;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(30, 30, 73, 25);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setBounds(30, 68, 83, 16);
		contentPane.add(lblLastName);
		
		JRadioButton rdbtnHigh = new JRadioButton("High");
		rdbtnHigh.setBounds(267, 142, 53, 25);
		contentPane.add(rdbtnHigh);
		
		JRadioButton rdbtnMedium = new JRadioButton("Medium");
		rdbtnMedium.setBounds(181, 142, 73, 25);
		contentPane.add(rdbtnMedium);
		
		JRadioButton rdbtnLow = new JRadioButton("Low");
		rdbtnLow.setBounds(111, 142, 53, 25);
		contentPane.add(rdbtnLow);
		
		JLabel lblExperience = new JLabel("Experience:");
		lblExperience.setBounds(30, 146, 73, 16);
		contentPane.add(lblExperience);
		
		textField = new JTextField();
		textField.setBounds(103, 31, 116, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(103, 65, 116, 22);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(103, 97, 116, 22);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(30, 97, 56, 16);
		contentPane.add(lblAge);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(323, 215, 97, 25);
		contentPane.add(btnSubmit);
	}
}
