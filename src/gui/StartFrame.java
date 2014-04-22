package src.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class StartFrame extends JFrame{

	public StartFrame(){
		setTitle("The Game of Life and Death");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLayout(new BorderLayout());
		
		add(new StevesWorld(), BorderLayout.CENTER);
		
		this.setPreferredSize(new Dimension(1200,700));
		this.pack();
	}
	
	public static void main(String[] args) {
		new StartFrame().setVisible(true);
	}
}
