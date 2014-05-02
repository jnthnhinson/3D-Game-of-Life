package src.graphics;

import com.threed.jpct.Camera;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

@SuppressWarnings("serial")
public class SteveCamera extends GameCamera{
	private final static float SPEED = 5f;
	private final static float PLAYER_HEIGHT = 30f;	
	private final static float GRAVITY = 4f;
	private final static float COLLISION_SPHERE_RADIUS = 8f;
	private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private String id = "STEVE";
	
	private int jumping;

	public SteveCamera(World world) {
		super(world);
		this.jumping = 0;
	}

	public void performMovement() {
		performGravity();
		jumping--;
	}

	private void performGravity() {
		SimpleVector camPos = getPosition();
		camPos.add(new SimpleVector(0, PLAYER_HEIGHT/2f, 0));
		SimpleVector dir;
		if (jumping > 0) {dir = new SimpleVector(0, -GRAVITY, 0);}
		else {dir = new SimpleVector(0, GRAVITY, 0);}
		dir = world.checkCollisionEllipsoid(camPos, dir, ELLIPSOID_RADIUS, 1);
		camPos.add(new SimpleVector(0, -PLAYER_HEIGHT/2f, 0));
		dir.x = 0;
		dir.z = 0;
		camPos.add(dir);
		setPosition(camPos);
	}
	
	public void updateStatus(String dir) {
		if(dir == "SL"){this.moveCamera(Camera.CAMERA_MOVELEFT, SPEED);}
		else if(dir == "SR"){this.moveCamera(Camera.CAMERA_MOVERIGHT, SPEED);}
		else if(dir == "F"){this.moveCamera(Camera.CAMERA_MOVEIN, SPEED);}
		else if(dir == "B"){this.moveCamera(Camera.CAMERA_MOVEOUT, SPEED);}
		else if(dir == "U"){updateJump();}
		else if(dir == "TL"){tiltCamera("TL");}
		else if(dir == "TR"){tiltCamera("TR");}
		else if(dir == "TU"){tilt(true);}
		else if(dir == "TD"){tilt(false);}
		else if(dir == "L"){turn(false);}
		else if(dir == "R"){turn(true);}
	}
	
	private void updateJump() {
		if (jumping <= 0) {
			jumping = 5;
		}
	}
	
	public void tiltCamera(String dir){
		if(dir == "TL"){
			rotateCameraZ((float)-.03);
		} else if(dir == "TR"){
			rotateCameraZ((float).03);
		}
	}
}
