package src.data_structures;

public enum CellSize {
	SMALL(13), MEDIUM(15), LARGE(20);
	
	private int size;
	
	private CellSize(int n){
		size = n;
	}
	
	public int getSize(){
		return size;
	}

}
