package src.graphics;

import com.threed.jpct.Camera;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

@SuppressWarnings("serial")
public class GodCamera extends GameCamera{
	//private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float PLAYER_HEIGHT = 30f;
	private final static float GRAVITY = 4f;

<<<<<<< HEAD
	
	private boolean left = false;
	private boolean right = false;
	private boolean up = false;
	private boolean down = false;
	private boolean forward = false;
	private boolean backward = false;
	private boolean jumping = false;
	private boolean falling = false;
	private boolean strafeLeft = false;
	private boolean strafeRight = false;
	private boolean tiltLeft = false;
	private boolean tiltRight = false;
	private boolean tiltUp = false;
	private boolean tiltDown = false;
	private boolean zoomOut = false;
	
	

	public GodCamera(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	public void updateStatus(String dir) {
		if(dir == "SL")			{strafeLeft = true;}
		else if(dir == "SR")	{strafeRight = true;}
		else if(dir == "F")		{forward = true;}
		else if(dir == "B")		{backward = true;}
		else if(dir == "U")		{jumping = true;}
		else if(dir == "D")		{falling = true;}
		else if(dir == "TL")	{tiltLeft = true;}
		else if(dir == "TR")	{tiltRight = true;}
		else if(dir == "TU")	{tiltUp = true;}
		else if(dir == "TD")	{tiltDown = true;}
		else if(dir == "L")		{left = true;}
		else if(dir == "R")		{right = true;}
	}
	
	public void performMovement() {
		performOtherMovement();
		resetBools();
	}
	

	private void performOtherMovement() {
		boolean cameraChanged = false;

		if (forward) {walk(true);cameraChanged = true;}
		else if (backward) {walk(false);cameraChanged = true;}
		if (left) {turn(false);}
		if (right) {turn(true);}
		if (tiltUp) {tilt(true);}
		if (tiltDown) {tilt(false);}
		if (tiltLeft) {tiltCamera("L");}
		if (tiltRight) {tiltCamera("R");}
		if (strafeLeft) {strafe(false); cameraChanged = true;}
		if (strafeRight) {strafe(true); cameraChanged = true;}
		if (jumping) {
			SimpleVector camPos = getPosition();
			SimpleVector dir = new SimpleVector(0, -GRAVITY, 0);
			camPos.add(dir);
			setPosition(camPos);
		}
		if (falling) {
			SimpleVector camPos = getPosition();
			SimpleVector dir = new SimpleVector(0, GRAVITY, 0);
			camPos.add(dir);
			setPosition(camPos);
		}
		if (cameraChanged) {moveCamera(new SimpleVector(0, -1, 0), PLAYER_HEIGHT/2f);}
	}
	
	/*
	public void performOtherMovement() {
		if(strafeLeft){moveCamera(Camera.CAMERA_MOVELEFT, 1);}
		else if(strafeRight){moveCamera(Camera.CAMERA_MOVERIGHT, 1);}
		else if(forward){moveCamera(Camera.CAMERA_MOVEIN, 1);}
		else if(backward){moveCamera(Camera.CAMERA_MOVEOUT, 1);}
		else if(jumping){moveCamera(Camera.CAMERA_MOVEUP, 1);}
		else if(falling){moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
	}
	*/
	public void resetBools() {
		strafeLeft = false;
		strafeRight = false;
		right = false;
		left = false;
		up = false;
		down = false;
		forward = false;
		backward = false;
		jumping = false;
		falling = false;
		strafeLeft = false;
		strafeRight = false;
		tiltLeft = false;
		tiltRight = false;
		tiltUp = false;
		tiltDown = false;
		zoomOut = false;
	}
	
	public void tiltCamera(String dir){
		if(dir == "L"){
			rotateCameraZ((float)-.03);
		} else if(dir == "R"){
			rotateCameraZ((float).03);
		}
	}

	
}
