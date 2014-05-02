package src.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PauseMenu extends Panel{
	private JButton quitButton, partyButton;
	private JTextArea instructions;
	private JToggleButton steve2God;
	
	private Perspective perspective;
	
	public PauseMenu(Perspective perspective){
		this.perspective = perspective;
		this.setBackground(Color.GRAY);
		
		initButtons();
		initInstructions();
		addComponents();
	}

	
	public void initButtons(){
		quitButton = new JButton("Exit Game");
		steve2God = new JToggleButton("Steve Mode");
		partyButton = new JButton("Party On");

		quitButton.setBackground(Color.BLACK);
		steve2God.setBackground(Color.BLACK);
		partyButton.setBackground(Color.BLACK);
		quitButton.setForeground(Color.WHITE);
		steve2God.setForeground(Color.WHITE);
		partyButton.setForeground(Color.WHITE);
		
		quitButton.addActionListener(new exitGame());
		steve2God.addActionListener(new steve2God());
		partyButton.addActionListener(new toggleParty());

	}
	
	public void addComponents(){
		this.add(quitButton);
		this.add(steve2God);
		this.add(partyButton);
		this.add(instructions);
	}
	
	
	public void initInstructions(){
		instructions = new JTextArea(" Forwards (w)\n Backwards (s)\n Left (a)\n Right(d)\n Rotate Left (q)\n Rotate Right (e)\n\n Ascend (space)\n Descend (shift) ");
		instructions.setBorder(BorderFactory.createLineBorder(Color.black));

	}
	
	private class exitGame implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			perspective.dispose();
		}
	}
	
	private class steve2God implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (steve2God.getText() == "Steve Mode"){
				steve2God.setText("God Mode");
			}
			else if (steve2God.getText() == "God Mode"){
				steve2God.setText("Steve Mode");
			}
			perspective.toggleCamera();
			perspective.requestFocus();
		}
	}
	
	private class OpenRuleChanger implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//boolean paused = false;
		}
	}
	
	private class ChangeCamera implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//boolean paused = false;
		}
	}
	
	private class toggleParty implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (partyButton.getText() == "Party On"){
				partyButton.setText("Party Off");
			}else{
				partyButton.setText("Party On");
			}
		}
	}
	
}
