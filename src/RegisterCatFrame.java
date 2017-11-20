import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class RegisterCatFrame extends JFrame {

	private JPanel contentPane;
	private JTextField nameField;
	private JTextField ageField;
	private JTextField breedField;
	private JTextField feeField;
	private CatAdoptionModel model;
	private JTextField genderField;
	
	public RegisterCatFrame(CatAdoptionModel catModel) {
		model = catModel;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(32, 34, 56, 16);
		contentPane.add(lblName);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(166, 76, 56, 16);
		contentPane.add(lblAge);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(32, 76, 56, 16);
		contentPane.add(lblGender);
		
		JLabel lblBreed = new JLabel("Breed:");
		lblBreed.setBounds(32, 119, 56, 16);
		contentPane.add(lblBreed);
		
		JLabel lblFee = new JLabel("Fee:");
		lblFee.setBounds(224, 119, 56, 16);
		contentPane.add(lblFee);
		
		nameField = new JTextField();
		nameField.setBounds(81, 31, 116, 22);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		ageField = new JTextField();
		ageField.setBounds(202, 73, 56, 22);
		contentPane.add(ageField);
		ageField.setColumns(10);
		
		breedField = new JTextField();
		breedField.setBounds(81, 113, 116, 22);
		contentPane.add(breedField);
		breedField.setColumns(10);
		
		feeField = new JTextField();
		feeField.setBounds(258, 116, 56, 22);
		contentPane.add(feeField);
		feeField.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				int age = Integer.parseInt(ageField.getText());
				String gender = genderField.getText();
				String breed = breedField.getText();
				double fee = Double.parseDouble(feeField.getText());
				try {
					model.insertCat(name, age, gender, breed, fee, model.getLocation());
					JOptionPane.showMessageDialog(null, name + " is registered for adoption!");
					AdminFrame adminFrame = new AdminFrame(model);
					adminFrame.setVisible(true);
					dispose();
				} catch (SQLException se) {
					System.out.println("Insertion failed");
					System.out.println(se.getMessage());
				}
			}		
		});
		btnRegister.setBounds(311, 215, 97, 25);
		contentPane.add(btnRegister);
		
		genderField = new JTextField();
		genderField.setBounds(81, 73, 50, 22);
		contentPane.add(genderField);
		genderField.setColumns(10);
	}

}
