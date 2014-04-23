package src.data_structures;

import com.threed.jpct.Object3D;
/*Currently the only constructor is a copy constructor
 *Create one object at instantiation and just use the copy constructor to clone it as many times as needed?
 *I'm not sure how the coordinate system works in JPCT
 */
public class Cell extends Object3D{
	
	private int pop;
	private int[] coordinates = {0, 0, 0};

	public Cell(Object3D ori) {
		super(ori);
	}
	
	public void incPop(boolean pol){
		if(pol){
			pop++;
		}else{
			pop--;
		}
	}
	
	public void setCoordinates(int x, int y, int z){
		coordinates[0] = x;
		coordinates[1] = y;
		coordinates[2] = z;
	}
	
	public int[] getCoordinates(){
		return coordinates;
	}
	
	

}
