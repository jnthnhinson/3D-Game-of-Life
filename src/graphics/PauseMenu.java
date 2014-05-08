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
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class PauseMenu extends Panel{
	private JButton quitButton, partyButton;
	private JTextArea instructions;
	private JToggleButton cameraToggle;
	private JSlider speedSlider;
	
	private Perspective perspective;
	
	public PauseMenu(Perspective perspective){
		this.perspective = perspective;
		this.setBackground(Color.GRAY);
		
		initButtons();
		initInstructions();
		initSlider();
		addComponents();
	}

	
	public void initButtons(){
		quitButton = new JButton("Exit Game");
		cameraToggle = new JToggleButton("Steve Mode");
		partyButton = new JButton("Party On");

		quitButton.setBackground(Color.BLACK);
		cameraToggle.setBackground(Color.BLACK);
		partyButton.setBackground(Color.BLACK);
		quitButton.setForeground(Color.WHITE);
		cameraToggle.setForeground(Color.WHITE);
		partyButton.setForeground(Color.WHITE);
		
		quitButton.addActionListener(new exitGame());
		cameraToggle.addActionListener(new cameraToggle());
		partyButton.addActionListener(new toggleParty());

	}
	
	public void initSlider(){
		int min = 0, medium = 10, max = 20;
		speedSlider = new JSlider(JSlider.HORIZONTAL, min, max, medium);
		speedSlider.addChangeListener(new speedChanger());
	}

	
	public void initInstructions(){
		instructions = new JTextArea(" Forwards (w)\n Backwards (s)\n Left (a)\n Right(d)\n Rotate Left (q)\n Rotate Right (e)\n\n Ascend (space)\n Descend (shift) ");
		instructions.setBorder(BorderFactory.createLineBorder(Color.black));

	}
	
	public void addComponents(){
		this.add(quitButton);
		this.add(cameraToggle);
		this.add(partyButton);
		this.add(speedSlider);
		this.add(instructions);
	}
	
	private class exitGame implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			perspective.dispose();
		}
	}
	
	private class cameraToggle implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if (cameraToggle.getText() == "Steve Mode"){
				cameraToggle.setText("God Mode");
			}
			else if (cameraToggle.getText() == "God Mode"){
				cameraToggle.setText("Steve Mode");
			}
			perspective.toggleCamera();
//			perspective.requestFocus();
		}
	}
	
	private class speedChanger implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent e) {
		    JSlider source = (JSlider)e.getSource();
		    perspective.changeSpeed(source.getValue()*100);
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
