import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;

public class LocationPanel extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public LocationPanel(CatAdoptionModel model) {
		setLayout(null);
		
		JLabel lblCatAdoptionCenter = new JLabel("CAT ADOPTION CENTER");
		lblCatAdoptionCenter.setBounds(79, 13, 291, 30);
		lblCatAdoptionCenter.setFont(new Font("Verdana", Font.PLAIN, 24));
		add(lblCatAdoptionCenter);
		
		JLabel lblEnterYourLocation = new JLabel("Enter your location:");
		lblEnterYourLocation.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblEnterYourLocation.setBounds(101, 54, 162, 20);
		add(lblEnterYourLocation);
		
		textField = new JTextField();
		textField.setBounds(101, 85, 140, 29);
		add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Change contents of the JList
			}
		});
		btnSearch.setBounds(251, 85, 92, 29);
		add(btnSearch);
		
		JList list = new JList();
		list.setBounds(45, 125, 354, 119);
		add(list);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				model.update(new WelcomePanel(model));
			}
		});
		btnSelect.setBounds(159, 255, 104, 34);
		add(btnSelect);

	}
}
