import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

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
					//TODO: move directory of adoptions
				}
			}
			
		});
	
			
	}
}
