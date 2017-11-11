import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CatAdoptionFrame extends JFrame implements ChangeListener {
	private CatAdoptionModel components;
	private JPanel contentPanel;
	private final int FRAME_WIDTH = 500;
	private final int FRAME_HEIGHT = 400;
	
	public CatAdoptionFrame(CatAdoptionModel model) {
		contentPanel = model.getPanel();
		
		components = model;
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);

		this.setContentPane(contentPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void stateChanged(ChangeEvent e) {
		JComponent c = components.getPanel();
		contentPanel.removeAll();
		contentPanel.add(c);
		this.setContentPane(contentPanel);
	}
}