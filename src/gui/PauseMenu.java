package src.gui;

import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class PauseMenu extends JFrame{
	
	public PauseMenu(){
		this.setPreferredSize(new Dimension(1000,750));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
