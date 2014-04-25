package src.graphics;

import com.threed.jpct.Camera;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

@SuppressWarnings("serial")
public class GodCamera extends GameCamera{
	//private final static SimpleVector ELLIPSOID_RADIUS = new SimpleVector(COLLISION_SPHERE_RADIUS,PLAYER_HEIGHT/2f,COLLISION_SPHERE_RADIUS);
	private final static float PLAYER_HEIGHT = 30f;

	public GodCamera(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}

	public void updateStatus(String dir) {
		if(dir == "L"){moveCamera(Camera.CAMERA_MOVELEFT, 1);}
		else if(dir == "R")		{moveCamera(Camera.CAMERA_MOVERIGHT, 1);}
		else if(dir == "F")		{moveCamera(Camera.CAMERA_MOVEIN, 1);}
		else if(dir == "B")		{moveCamera(Camera.CAMERA_MOVEOUT, 1);}
		else if(dir == "U")		{moveCamera(Camera.CAMERA_MOVEUP, 1);}
		else if(dir == "D")		{moveCamera(Camera.CAMERA_MOVEDOWN, 1);}
		else if(dir == "TL")	{tiltCamera("L");}
		else if(dir == "TR")	{tiltCamera("R");}
	}
	public void tiltCamera(String dir){
		if(dir == "L"){
			rotateCameraZ((float)-.03);
		} else if(dir == "R"){
			rotateCameraZ((float).03);
		}
	}

	
}
