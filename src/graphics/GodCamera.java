package src.graphics;

import com.threed.jpct.Camera;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

@SuppressWarnings("serial")
public class GodCamera extends GameCamera{
	private final static float SPEED = 5f;
	private final static float PLAYER_HEIGHT = 30f;	
	public GodCamera(World world) {
		super(world);
	}


	public void jump(){
		SimpleVector camPos = getPosition();
		SimpleVector dir = new SimpleVector(0, -SPEED, 0);
		camPos.add(dir);
		setPosition(camPos);
	}
	
	public void fall(){
		SimpleVector camPos = getPosition();
		SimpleVector dir = new SimpleVector(0, SPEED, 0);
		camPos.add(dir);
		setPosition(camPos);
	}
	
	public void updateStatus(String dir) {
		if(dir == "SL"){this.moveCamera(Camera.CAMERA_MOVELEFT, SPEED);}
		else if(dir == "SR"){this.moveCamera(Camera.CAMERA_MOVERIGHT, SPEED);}
		else if(dir == "F"){this.moveCamera(Camera.CAMERA_MOVEIN, SPEED);}
		else if(dir == "B"){this.moveCamera(Camera.CAMERA_MOVEOUT, SPEED);}
		else if(dir == "U"){this.jump();}
		else if(dir == "D"){this.fall();}
		else if(dir == "TL"){tiltCamera("TL");}
		else if(dir == "TR"){tiltCamera("TR");}
	}
	
	public void tiltCamera(String dir){
		if(dir == "TL"){
			rotateCameraZ((float)-.03);
		} else if(dir == "TR"){
			rotateCameraZ((float).03);
		}
	}
}
