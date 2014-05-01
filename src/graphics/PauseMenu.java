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
	private PauseMenu menu;
	private ArrayList<JButton> buttons;
	private Border lineBorder;
	private Perspective perspective;
	private WorldBuilder wb;
	private JToggleButton steve2God;
	
	public PauseMenu(Perspective perspective, WorldBuilder wb){
		menu = this;
		this.setPreferredSize(new Dimension(400,250));
		this.wb = wb;
		this.perspective = perspective;
		buttons = new ArrayList<JButton>();
		
		JButton closeMenuButton = new JButton("Close Menu");
		JButton quitButton = new JButton("Exit Game");
		steve2God = new JToggleButton("Steve Mode");

		JTextArea instructions = new JTextArea(" Forwards (w)\n Backwards (s)\n Left (a)\n Right(d)\n Rotate Left (q)\n Rotate Right (e)\n\n Ascend (space)\n Descend (shift) ");
		lineBorder = BorderFactory.createLineBorder(Color.black);
		instructions.setBorder(lineBorder);
		
		closeMenuButton.addActionListener(new closeMenu());
		quitButton.addActionListener(new exitGame());
		steve2God.addActionListener(new steve2God());


		buttons.add(closeMenuButton);buttons.add(quitButton);
		this.add(steve2God);
		for(JButton button: buttons){
			this.add(button);
		}
		this.add(instructions);
	}

	private class closeMenu implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			perspective.togglePause();
		}
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
				wb.setMode("god");
			}
			else if (steve2God.getText() == "God Mode"){
				steve2God.setText("Steve Mode");
				wb.setMode("steve");
			}
			
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
	
}
