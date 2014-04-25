package src.graphics;

import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

@SuppressWarnings("serial")
public class SteveCamera extends GameCamera {
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
	private boolean zoomOut = false;

	public SteveCamera(World world) {
		super(world);
		//setFOV(getMinFOV());
	}

	public void updateStatus(String dir) {
		if(dir == "L")			{strafeLeft = true;}
		else if(dir == "R")		{strafeRight = true;}
		else if(dir == "F")		{forward = true;}
		else if(dir == "B")		{backward = true;}
		else if(dir == "U")		{jumping = true;}
		//else if(dir == "D")		{zoomOut = true;}
		else if(dir == "TL")	{tiltLeft = true;}
		else if(dir == "TR")	{tiltRight = true;}
	}
	
	public void performMovement() {
		performGravity();
		performOtherMovement();
		resetBools();
	}
	
	private void performGravity() {
		SimpleVector camPos = getPosition();
		camPos.add(new SimpleVector(0, PLAYER_HEIGHT/2f, 0));
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
	
	public void resetBools() {
		left = false;
		right = false;
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
		zoomOut = false;
	}
	
	
	
}
