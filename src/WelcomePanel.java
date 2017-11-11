import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WelcomePanel extends JPanel{

	public WelcomePanel(CatAdoptionModel model) {
		setLayout(null);

		JButton btnViewAllCats = new JButton("View all cats medical history");
		add(btnViewAllCats);

		btnViewAllCats.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.searchCats();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		add(btnViewAllCats);
	}
}
