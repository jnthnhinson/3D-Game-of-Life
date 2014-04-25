package src.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class PauseMenu extends JPanel{
	private PauseMenu menu;
	private ArrayList<JButton> buttons;
	private Border lineBorder;
	private Perspective perspective;
	
	public PauseMenu(Perspective perspective){
		menu = this;
		this.setPreferredSize(new Dimension(400,250));
		this.perspective = perspective;
		buttons = new ArrayList<JButton>();
		
		JButton closeMenuButton = new JButton("Close Menu");
		JButton quitButton = new JButton("Exit Game");
//		JButton b3 = new JButton("Is");
//		JButton b4 = new JButton("The");
//		JButton b5 = new JButton("Pause Menu");
		JTextArea instructions = new JTextArea(" Forwards (w)\n Backwards (s)\n Left (a)\n Right(d)\n Rotate Left (q)\n Rotate Right (e)\n\n Ascend (space)\n Descend (shift) ");
		lineBorder = BorderFactory.createLineBorder(Color.black);
		instructions.setBorder(lineBorder);
		
		closeMenuButton.addActionListener(new closeMenu());
		quitButton.addActionListener(new exitGame());
//		b3.addActionListener(new OpenRuleChanger());
//		b4.addActionListener(new ChangeCamera());
//		b5.addActionListener(new Resume());

		buttons.add(closeMenuButton);buttons.add(quitButton);//buttons.add(b3);buttons.add(b4);buttons.add(b5);

		for(JButton button: buttons){
			this.add(button);
		}
		this.add(instructions);
	}
	@Override
	public void paintComponent(Graphics g){
		repaint();
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
	
	private class SetWorldSize implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			//boolean paused = false;
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
