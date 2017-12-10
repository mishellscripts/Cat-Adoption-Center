
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

/**
 * Allows user to find cats according to breed/age/gender preferences
 * @author blanchypolangcos
 */

public class FindMatchFrame extends JFrame {

	private CatAdoptionModel model;
	private JPanel contentPane;
	private static final int WIDTH = 270;
	private static final int HEIGHT = 32;
	
	public FindMatchFrame(CatAdoptionModel catModel) {
		model = catModel;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(5,2));
		
		JLabel lblprompt = new JLabel("Enter preferences and search.");
		JLabel lblage = new JLabel("Age (1, 2, 3...): ");
		JLabel lblgender = new JLabel("Gender (M/F): ");
		JLabel lblbreed = new JLabel("Breed: ");
		
		JTextField inAge= new JTextField();
		inAge.setSize(WIDTH, HEIGHT);
		JTextField inGender= new JTextField();
		inGender.setSize(WIDTH, HEIGHT);
		JTextField inBreed= new JTextField();
		inBreed.setSize(WIDTH, HEIGHT);
		
		JButton cancel = new JButton("Cancel");
		cancel.setSize(WIDTH, HEIGHT);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserFrame uFrame = new UserFrame(model);
				uFrame.setLocationRelativeTo(null);
				uFrame.setVisible(true);
				dispose();
			}
		});
		
		JButton search = new JButton("Search");
		search.setSize(WIDTH, HEIGHT);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HashMap<String, String> preferences = new HashMap<String,String>();
				if (!inAge.getText().isEmpty()) {
					preferences.put("age", inAge.getText());
				}
				if (!inGender.getText().isEmpty()) {
					preferences.put("gender", inGender.getText().toUpperCase());
				}
				if (!inBreed.getText().isEmpty()) {
					preferences.put("breed", inBreed.getText().toLowerCase());
				}
				if (preferences.size() > 0) {
					CatDirectoryFrame cdf = new CatDirectoryFrame(model, preferences);
					cdf.setLocationRelativeTo(null);
					cdf.setVisible(true);
					dispose();
				}
			}
		});
		
		contentPane.add(lblprompt);
		contentPane.add(new JLabel());
		contentPane.add(lblage);
		contentPane.add(inAge);
		contentPane.add(lblgender);
		contentPane.add(inGender);
		contentPane.add(lblbreed);
		contentPane.add(inBreed);
		contentPane.add(cancel);
		contentPane.add(search);
		
		setVisible(true);
		
	}
	
}
