import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
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
		lblName.setFont(new Font("Arial", Font.PLAIN, 13));
		lblName.setBounds(32, 34, 56, 16);
		contentPane.add(lblName);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setFont(new Font("Arial", Font.PLAIN, 13));
		lblAge.setBounds(143, 76, 56, 16);
		contentPane.add(lblAge);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setFont(new Font("Arial", Font.PLAIN, 13));
		lblGender.setBounds(32, 76, 56, 16);
		contentPane.add(lblGender);
		
		JLabel lblBreed = new JLabel("Breed:");
		lblBreed.setFont(new Font("Arial", Font.PLAIN, 13));
		lblBreed.setBounds(32, 119, 56, 16);
		contentPane.add(lblBreed);
		
		JLabel lblFee = new JLabel("Fee:");
		lblFee.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFee.setBounds(224, 119, 56, 16);
		contentPane.add(lblFee);
		
		nameField = new JTextField();
		nameField.setBounds(81, 31, 116, 25);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		ageField = new JTextField();
		ageField.setBounds(172, 73, 56, 25);
		contentPane.add(ageField);
		ageField.setColumns(10);
		
		breedField = new JTextField();
		breedField.setBounds(81, 113, 116, 25);
		contentPane.add(breedField);
		breedField.setColumns(10);
		
		feeField = new JTextField();
		feeField.setBounds(260, 115, 56, 25);
		contentPane.add(feeField);
		feeField.setColumns(10);
		
		genderField = new JTextField();
		genderField.setBounds(81, 73, 50, 25);
		contentPane.add(genderField);
		genderField.setColumns(10);
		
		JButton btnRegister = new JButton("Donate");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				int age = Integer.parseInt(ageField.getText());
				String gender = genderField.getText();
				String breed = breedField.getText();
				double fee = Double.parseDouble(feeField.getText());
				try {
					model.insertCat(name, age, gender, breed, fee, model.getLocationID());
					JOptionPane.showMessageDialog(null, name + " is registered for adoption!");
					AdminFrame adminFrame = new AdminFrame(model);
					adminFrame.setLocationRelativeTo(null);
					adminFrame.setVisible(true);
					dispose();
				} catch (SQLException se) {
					System.out.println("Insertion failed");
					System.out.println(se.getMessage());
				}
			}		
		});
		btnRegister.setBounds(323, 225, 97, 25);
		contentPane.add(btnRegister);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 11));
		btnCancel.setBounds(10, 227, 89, 23);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminFrame aFrame = new AdminFrame(model);
				aFrame.setLocationRelativeTo(null);
				aFrame.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnCancel);
	}

}
