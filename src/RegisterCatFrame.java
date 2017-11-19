import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

public class RegisterCatFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterCatFrame frame = new RegisterCatFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterCatFrame() {
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
		lblAge.setBounds(221, 34, 56, 16);
		contentPane.add(lblAge);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(32, 76, 56, 16);
		contentPane.add(lblGender);
		
		JLabel lblBreed = new JLabel("Breed:");
		lblBreed.setBounds(32, 119, 56, 16);
		contentPane.add(lblBreed);
		
		JLabel lblFee = new JLabel("Fee:");
		lblFee.setBounds(221, 119, 56, 16);
		contentPane.add(lblFee);
		
		textField = new JTextField();
		textField.setBounds(81, 31, 116, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(256, 31, 56, 22);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setBounds(96, 72, 71, 25);
		contentPane.add(rdbtnFemale);
		
		JRadioButton rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setBounds(181, 72, 63, 25);
		contentPane.add(rdbtnMale);
		
		textField_2 = new JTextField();
		textField_2.setBounds(81, 113, 116, 22);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(256, 116, 56, 22);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnRegister = new JButton("Register");
		btnRegister.setBounds(311, 215, 97, 25);
		contentPane.add(btnRegister);
	}

}
