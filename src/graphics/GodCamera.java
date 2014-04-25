package src.graphics;

import com.threed.jpct.Camera;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

@SuppressWarnings("serial")
public class GodCamera extends GameCamera{
	//private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float PLAYER_HEIGHT = 30f;
	private final static float GRAVITY = 1f;
	

	public GodCamera(World world) {
		super(world);
	}

	public void updateStatus(String dir) {
		if(dir == "SL"){moveCamera(Camera.CAMERA_MOVELEFT, 1);}
 		else if(dir == "SR")		{moveCamera(Camera.CAMERA_MOVERIGHT, 1);}
 		else if(dir == "F")		{moveCamera(Camera.CAMERA_MOVEIN, 1);}
 		else if(dir == "B")		{moveCamera(Camera.CAMERA_MOVEOUT, 1);}
 		else if(dir == "U")		{this.jump();}
 		else if(dir == "D")		{this.fall();}
		else if(dir == "TL")	{tiltCamera("L");}
 		else if(dir == "TR")	{tiltCamera("R");}
		else if(dir == "TU")	{this.jump();}
		else if(dir == "TD")	{this.fall();}
	}
	

	public void jump(){
		SimpleVector camPos = getPosition();
		SimpleVector dir = new SimpleVector(0, -GRAVITY, 0);
		camPos.add(dir);
		setPosition(camPos);
	}
	
	public void fall(){
		SimpleVector camPos = getPosition();
		SimpleVector dir = new SimpleVector(0, GRAVITY, 0);
		camPos.add(dir);
		setPosition(camPos);
	}
	
	public void tiltCamera(String dir){
		if(dir == "L"){
			rotateCameraZ((float)-.03);
		} else if(dir == "R"){
			rotateCameraZ((float).03);
		}
	}

	
}
