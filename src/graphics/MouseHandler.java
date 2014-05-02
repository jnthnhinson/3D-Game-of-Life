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
	Perspective view;
	GameCamera camera;
	Timer timer;
	
	boolean stopped = false;
	int lastMousePosX, lastMousePosY;
	int mouseOffsetX, mouseOffsetY;
	int middleX, middleY;

	public MouseHandler(Perspective view, GameCamera camera){
		this.view = view;
		this.camera = camera;
		this.timer = new Timer(1, this);
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
			camera.rotateView(mouseOffsetX, mouseOffsetY);
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
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { 
		if(stopped == false){
			setMiddlePositions();
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
	public void mousePressed(MouseEvent e) { 
		this.view.selectPointedObject();
	}

	public void setMiddlePositions(){
		Point locOnScreen = this.view.getLocationOnScreen();
		middleX = locOnScreen.x + (this.view.getWidth() / 2);
		middleY = locOnScreen.y + (this.view.getHeight() / 2);
	}
	
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
