package src.graphics;

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.Timer;

public class MouseHandler extends MouseMotionAdapter implements MouseListener, ActionListener{
	CameraTest camTest;
	boolean stopped = false;

	int degree;
	int lastMousePosX, lastMousePosY;
	int mouseOffsetX, mouseOffsetY;
	int curPosX, curPosY;
	Timer timer;

	public MouseHandler(CameraTest ct){
		this.camTest = ct;
		this.timer = new Timer(3, this);
		this.timer.start();
	}

	public void toggleListener(){
		if(stopped == true){ stopped = false;
		}else { stopped = true; }
	}

	@Override
	public void mouseMoved(MouseEvent e){
		if(stopped == false){
			updateCurPos(e);
			updateMouseMovement(e);
			this.camTest.rotateView(mouseOffsetX, mouseOffsetY);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e){	
		//		updateMouseMovement(e);
		//		this.camTest.rotateView(mouseOffsetX, mouseOffsetY);
	}

	public void updateMouseMovement(MouseEvent e){
		updateCurPos(e);
		updateLastPos(e);
		updateMouseOffset(e);
		setLastToCurrent(e);		
	}

	public void updateCurPos(MouseEvent e){
		curPosX = e.getX();
		curPosY = e.getY();
	}

	public void updateLastPos(MouseEvent e){
		if(lastMousePosX == 0 || lastMousePosY == 0){
			lastMousePosX = e.getX();
			lastMousePosY = e.getY();
		}		
	}
	public void updateMouseOffset(MouseEvent e){
		mouseOffsetX = e.getX() - lastMousePosX;
		mouseOffsetY = lastMousePosY - e.getY();
	}
	public void setLastToCurrent(MouseEvent e){
		lastMousePosX = e.getX();
		lastMousePosY = e.getY();
	}

	//~~~~~~~~~~~~~~~~mouse listener methods~~~~~~~~~~~~~~~~~~~~~~~//

	@Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { 
		if(stopped == false){
			Point locOnScreen = this.camTest.getLocationOnScreen();
			int middleX = locOnScreen.x + (this.camTest.getWidth() / 2);
			int middleY = locOnScreen.y + (this.camTest.getHeight() / 2);
			try{
				Robot rob = new Robot();
				rob.mouseMove(middleX, middleY);
			}catch(Exception ex){System.out.println(ex);}
			this.resetMousePositions();
		}
	}
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