package src.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class KeyHandler implements KeyListener, ActionListener {
	private int W, A, S, D, SPACE, ESC;
	private boolean left = false, right = false, up = false, down = false, space = false, esc = false;

	private Timer timer;
	
	public KeyHandler(){
		this.timer = new Timer(3, this);
		this.timer.start();
		
		initKeyCodes();
	}
	
	public void initKeyCodes(){
		W = KeyEvent.VK_W;
		A = KeyEvent.VK_A;
		S = KeyEvent.VK_S;
		D = KeyEvent.VK_D;
		SPACE = KeyEvent.VK_SPACE;
		ESC = KeyEvent.VK_ESCAPE;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == A) {left = true; }
		if (key == D) {right = true; }
		if (key == W) {up = true; }
		if (key == S) {down = true; }
		if (key == SPACE) {space = true; }
		if (key == ESC) {esc = !esc; }
	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if (key == A) {left = false; }
		if (key == D) {right = false; }
		if (key == W) {up = false; }
		if (key == S) {down = false; }
		if (key == SPACE) {space = false; }
	}
	@Override
	public void keyTyped(KeyEvent e){ }
	
}