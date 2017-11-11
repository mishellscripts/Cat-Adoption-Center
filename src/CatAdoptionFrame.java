import java.awt.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CatAdoptionFrame extends JFrame implements ChangeListener{
	private CatAdoptionModel components;
	private JPanel contentPanel;
	private final int FRAME_WIDTH = 700;
	private final int FRAME_HEIGHT = 500;
	
	public CatAdoptionFrame(CatAdoptionModel model) {
		contentPanel.setLayout(new BorderLayout());
		
		components = model;
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		contentPanel.add(model.getPanel());
		
		
		this.setContentPane(contentPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void stateChanged(ChangeEvent e) {
		JComponent a = components.getPanel();
		contentPanel.removeAll();
		contentPanel.add(a);
		this.setContentPane(contentPanel);
	}
}