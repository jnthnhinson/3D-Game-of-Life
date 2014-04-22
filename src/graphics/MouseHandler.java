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
	Timer timer;
	boolean stopped = false;
	int lastMousePosX, lastMousePosY;
	int mouseOffsetX, mouseOffsetY;

	public MouseHandler(CameraTest ct){
		this.camTest = ct;
		this.timer = new Timer(5, this);
		this.timer.start();
	}

	public void toggleListener(){
		if(stopped == true){ stopped = false;
		}else { stopped = true; }
	}

	@Override
	public void mouseMoved(MouseEvent e){
		if(stopped == false){
			updateMouseMovement(e);
			this.camTest.rotateView(mouseOffsetX, mouseOffsetY);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e){	}

	public void updateMouseMovement(MouseEvent e){
		updateLastPos(e);
		updateMouseOffset(e);
		setLastToCurrent(e);		
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
	public void mouseClicked(MouseEvent e) { this.camTest.addBlock();}
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
	public void mouseReleased(MouseEvent e) { }
	@Override
	public void mousePressed(MouseEvent e) { }


	public void resetMousePositions(){
		this.lastMousePosX = 0;
		this.lastMousePosY = 0;
		this.mouseOffsetX = 0;
		this.mouseOffsetY = 0;
	}

	//~~~~~~~~~~~~~~~~~~action listener~~~~~~~~~~~~~~~~~~~~~~~~//

	@Override
	public void actionPerformed(ActionEvent arg0) { }
}