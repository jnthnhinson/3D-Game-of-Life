package src.data_structures;

import java.awt.Color;
import java.util.Random;


public class RandomColor{
	private Color partyColor;
	
	public Color randomColor() {
		Random rand = new Random();
		partyColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
		return partyColor;
	}
	
	public static Color randCol(){
		Random rand = new Random();
		return new Color(rand.nextInt(256/2), rand.nextInt(256/2), rand.nextInt(256)/2);
	}
}