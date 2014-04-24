package src.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class KeyHandler implements KeyListener, ActionListener {
	private int W, A, S, D, Q, E, SPACE, ESC, SHIFT;
	private boolean left = false, right = false, forward = false, back = false, tiltR = false, tiltL = false, shift = false, space = false, esc = false;

	private Timer timer;
	private Perspective perspective;
	private GameCamera camera;
	
	public KeyHandler(Perspective perspective, GameCamera camera){
		this.timer = new Timer(10, this);
		this.timer.start();
		
		this.perspective = perspective;
		this.camera = camera;
		initKeyCodes();
	}
	
	public void initKeyCodes(){
		W = KeyEvent.VK_W;
		A = KeyEvent.VK_A;
		S = KeyEvent.VK_S;
		D = KeyEvent.VK_D;
		Q = KeyEvent.VK_Q;
		E = KeyEvent.VK_E;
		ESC = KeyEvent.VK_ESCAPE;
		SPACE = KeyEvent.VK_SPACE;
		SHIFT = KeyEvent.VK_SHIFT;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(left == true){
			camera.updateStatus("L");
		} if(right == true){
			camera.updateStatus("R");
		} if(forward == true){
			camera.updateStatus("F");
		} if(back == true){
			camera.updateStatus("B");
		} if(space == true){
			camera.updateStatus("U");
		} if(shift == true){
			camera.updateStatus("D");
		} if(tiltR == true){
			camera.updateStatus("TR");
		} if(tiltL == true){
			camera.updateStatus("TL");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == A) {left = true; }
		if (key == D) {right = true; }
		if (key == W) {forward = true; }
		if (key == S) {back = true; }
		if (key == Q) {tiltL = true; }
		if (key == E) {tiltR = true; }
		if (key == SPACE) {space = true; }
		if (key == SHIFT) {shift = true; }
		if (key == ESC) { perspective.toggleCursor(); }
	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if (key == A) {left = false; }
		if (key == D) {right = false; }
		if (key == W) {forward = false; }
		if (key == S) {back = false; }
		if (key == Q) {tiltL = false; }
		if (key == E) {tiltR = false; }
		if (key == SPACE) {space = false; }
		if (key == SHIFT) {shift = false; }
	}
	@Override
	public void keyTyped(KeyEvent e){ }
	
}