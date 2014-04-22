package src.graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import javax.swing.Timer;

public class MouseHandler extends MouseMotionAdapter implements MouseListener, ActionListener{
	CameraTest camTest;
	boolean attacking = false;
	int degree;
	int lastMousePosX, lastMousePosY;
	int mouseOffsetX, mouseOffsetY;
	Timer timer;

	public MouseHandler(CameraTest ct){
		this.camTest = ct;
		this.timer = new Timer(3, this);
		this.timer.start();
	}

	@Override
	public void mouseDragged(MouseEvent e){	

		if(lastMousePosX == 0 || lastMousePosY == 0){
			lastMousePosX = e.getX();
			lastMousePosY = e.getY();
		}


		int newMousePosX = e.getX();
		int newMousePosY = e.getY();

		mouseOffsetX = newMousePosX - lastMousePosX;
		mouseOffsetY = lastMousePosY - newMousePosY;
		
		this.camTest.rotateView(mouseOffsetX, mouseOffsetY);
		
		lastMousePosX = newMousePosX;
		lastMousePosY = newMousePosY;


	}

	//~~~~~~~~~~~~~~~~mouse listener methods~~~~~~~~~~~~~~~~~~~~~~~//

	@Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
	@Override
	public void mouseReleased(MouseEvent e) { 
		resetMousePositions();
	}
	@Override
	public void mousePressed(MouseEvent e) {
	}

	
	public void resetMousePositions(){
		this.lastMousePosX = 0;
		this.lastMousePosY = 0;
		this.mouseOffsetX = 0;
		this.mouseOffsetY = 0;
	}

	//~~~~~~~~~~~~~~~~~~action listener~~~~~~~~~~~~~~~~~~~~~~~~//

	@Override
	public void actionPerformed(ActionEvent arg0) {

	}
}