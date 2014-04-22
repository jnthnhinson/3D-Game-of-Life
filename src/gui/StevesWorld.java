package src.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StevesWorld extends JPanel implements ActionListener{

	public StevesWorld(){
		this.addKeyListener(new KeyHandler());
	}
	public void actionPerformed(ActionEvent e){
		repaint();
	}
}
