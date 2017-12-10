import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Return a cat which was previously adopted
 * @author blanchypolangcos
 *
 */
public class ReturnCatFrame extends JFrame {

	private JPanel contentPane;
	private CatAdoptionModel model;
	
	public ReturnCatFrame(CatAdoptionModel model) {
		this.model = model;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2,2,10,10));
		
		JLabel lblprompt = new JLabel("Enter user ID: ");
		JTextField inID = new JTextField();
		
		JButton back = new JButton("Cancel");
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserFrame uFrame = new UserFrame(model);
				uFrame.setLocationRelativeTo(null);
				uFrame.setVisible(true);
				dispose();
			}
			
		});
		
		JButton enter = new JButton("See Your Adoptions");
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!inID.getText().isEmpty()) {
					//System.out.println("Getting directory from ReturnCatFrame");
					CatDirectoryFrame cdf = new CatDirectoryFrame(model, new UserFrame(model), inID.getText());
					dispose();
				}
			}
			
		});
	
		contentPane.add(lblprompt);
		contentPane.add(inID);
		contentPane.add(back);
		contentPane.add(enter);
		setVisible(true);
	}
}
