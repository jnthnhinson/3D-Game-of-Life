package src.graphics;

import com.threed.jpct.*;

@SuppressWarnings("serial")
public class GameCamera extends Camera {
	private final static float COLLISION_SPHERE_RADIUS = 8f;
	private final static float PLAYER_HEIGHT = 30f;
	private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float GRAVITY = 4f;
	private final static float MOVE_SPEED = 2.5f;
	private final static float TURN_SPEED = 0.06f;
	
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
	
	private World world;
	private Matrix playerDirection;
	private SimpleVector tempVector=new SimpleVector();
	
	
	public GameCamera(World world) {
		this.world = world;
		this.playerDirection = new Matrix();
	}
	/*
	public void updateStatus(String dir) {
		if(dir == "L"){moveCamera(Camera.CAMERA_MOVELEFT, 1);}
		else if(dir == "R"){moveCamera(Camera.CAMERA_MOVERIGHT, 1);}
		else if(dir == "F"){moveCamera(Camera.CAMERA_MOVEIN, 1);}
		else if(dir == "B"){moveCamera(Camera.CAMERA_MOVEOUT, 1);}
		else if(dir == "U"){moveCamera(Camera.CAMERA_MOVEUP, 1);}
		else if(dir == "D"){moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
		else if(dir == "TL"){moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
		else if(dir == "TR"){moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
	}
	*/
	public void updateStatus(String dir) {
		if(dir == "L")			{strafeLeft = true;}
		else if(dir == "R")		{strafeRight = true;}
		else if(dir == "F")		{forward = true;}
		else if(dir == "B")		{backward = true;}
		else if(dir == "U")		{jumping = true;}
		else if(dir == "D")		{falling = true;}
		else if(dir == "TL")	{tiltLeft = true;}
		else if(dir == "TR")	{tiltRight = true;}
	}
	
	public void tiltCamera(String dir){
		if(dir == "L"){
			rotateCameraZ((float)-.03);
		} else if(dir == "R"){
			rotateCameraZ((float).03);
		}
	}

	public void rotateView(int dx, int dy){	
		if (dx !=0 || dy != 0){
			SimpleVector line =new SimpleVector(dx, 0, dy);
			Matrix m = line.normalize().getRotationMatrix();

			m.rotateAxis(m.getXAxis(), (float) -Math.PI/2f);
			rotateAxis(m.invert3x3().getXAxis(), line.length() / 500f);
		}
	}
	
	// BELOW IS NEW
	
	public void performMovement() {
		performGravity();
		performOtherMovement();
	}
	
	private void performGravity() {
		SimpleVector camPos = getPosition();
		camPos.add(new SimpleVector(0, PLAYER_HEIGHT/2f, 0));
		//SimpleVector dir = new SimpleVector(0, GRAVITY, 0);
		SimpleVector dir;
		if (jumping) {dir = new SimpleVector(0, -GRAVITY, 0);}
		else {dir = new SimpleVector(0, GRAVITY, 0);}
		dir = world.checkCollisionEllipsoid(camPos, dir, ELLIPSOID_RADIUS, 1);
		camPos.add(new SimpleVector(0, -PLAYER_HEIGHT/2f, 0));
		dir.x = 0;
		dir.z = 0;
		camPos.add(dir);
		setPosition(camPos);
	}

	private void performOtherMovement() {
		boolean cameraChanged = false;

		if (forward) {walk(true);cameraChanged = true;}
		else if (backward) {walk(false);cameraChanged = true;}
		if (left) {turn(false);}
		if (right) {turn(true);}
		if (up) {tilt(true);}
		if (down) {tilt(false);}
		if (strafeLeft) {strafe(false); cameraChanged = true;}
		if (strafeRight) {strafe(true); cameraChanged = true;}
		if (cameraChanged) {moveCamera(new SimpleVector(0, -1, 0), PLAYER_HEIGHT/2f);}
	}

	private void walk(boolean isForward) {
		moveCamera(new SimpleVector(0,1,0), PLAYER_HEIGHT/2f);
		tempVector = playerDirection.getZAxis();
		if (!isForward) {tempVector.scalarMul(-1f);}
		world.checkCameraCollisionEllipsoid(tempVector, ELLIPSOID_RADIUS, MOVE_SPEED, 5);
	}

	private void tilt(boolean isUp) {
		float turnSpeed = TURN_SPEED;
		if (!isUp) {turnSpeed *= -1;}
		rotateX(turnSpeed);
	}

	private void turn(boolean isRight) {
		float turnSpeed = TURN_SPEED;
		if (!isRight) {turnSpeed *= -1;}
		rotateAxis(getBack().getYAxis(), turnSpeed);
		playerDirection.rotateY(turnSpeed);
	}

	private void strafe(boolean isRight) {
		moveCamera(new SimpleVector(0,1,0), PLAYER_HEIGHT/2f);
		tempVector=playerDirection.getXAxis();
		if (!isRight) {tempVector.scalarMul(-1f);}
		world.checkCameraCollisionEllipsoid(tempVector, ELLIPSOID_RADIUS, MOVE_SPEED, 5);
	}
	
}
