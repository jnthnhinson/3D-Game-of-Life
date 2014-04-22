package src.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

public class KeyHandler implements KeyListener, ActionListener {
	private int W, A, S, D, SPACE, ESC, SHIFT;
	private boolean left = false, right = false, forward = false, back = false, down = false, space = false, esc = false;

	private Timer timer;
	private CameraTest camTest;
	
	public KeyHandler(CameraTest camTest){
		this.timer = new Timer(10, this);
		this.timer.start();
		
		this.camTest = camTest;
		initKeyCodes();
	}
	
	public void initKeyCodes(){
		W = KeyEvent.VK_W;
		A = KeyEvent.VK_A;
		S = KeyEvent.VK_S;
		D = KeyEvent.VK_D;
		SPACE = KeyEvent.VK_SPACE;
		SHIFT = KeyEvent.VK_SHIFT;
		ESC = KeyEvent.VK_ESCAPE;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(left == true){
			this.camTest.moveCamera("L");
		} if(right == true){
			this.camTest.moveCamera("R");
		} if(forward == true){
			this.camTest.moveCamera("F");
		} if(back == true){
			this.camTest.moveCamera("B");
		} if(space == true){
			this.camTest.moveCamera("U");
		} if(down == true){
			this.camTest.moveCamera("D");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == A) {left = true; }
		if (key == D) {right = true; }
		if (key == W) {forward = true; }
		if (key == S) {back = true; }
		if (key == SPACE) {space = true; }
		if (key == SHIFT) {down = true; }
	}
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if (key == A) {left = false; }
		if (key == D) {right = false; }
		if (key == W) {forward = false; }
		if (key == S) {back = false; }
		if (key == SPACE) {space = false; }
		if (key == SHIFT) {down = false; }
	}
	@Override
	public void keyTyped(KeyEvent e){ }
	
}