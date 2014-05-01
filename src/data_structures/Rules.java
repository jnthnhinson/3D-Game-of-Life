package src.data_structures;

public enum Rules {
	
	THREEDGROWTH{

		@Override
		public void apply(CellManager cm, Cell c) {
			if (cm.totalNeighbors(c) <= 1 && c.isDead(c)) {
				c.murderCell(c);
			}
			else if (cm.totalNeighbors(c) >= 5 && cm.totalNeighbors(c) < 8) {
				c.birthCell(c);
			}
			else if (cm.totalNeighbors(c) >= 8) {
				c.murderCell(c);
			}
			else if (cm.numFlatNeighbors(c) > 4) {
				c.murderCell(c);
			}
		}
		
	};
	
	public abstract void apply(CellManager cm, Cell c);

}
