package src.data_structures;

public enum EdgeLength {
	TEST(6), TINY(12), SMALL(18), MEDIUM(24), LARGE(30), HUGE(50);
	
	private int length;
	
	private EdgeLength(int n){
		length = n;
	}

	public int length(){
		return length;
	}
	
}
