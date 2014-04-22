package src.data_structures;

public enum CellSize {
	SMALL(1), MEDIUM(2), LARGE(3);
	
	private int size;
	
	private CellSize(int n){
		size = n;
	}
	
	public int getSize(){
		return size;
	}

}
