package src.data_structures;

import java.awt.Color;

public enum Stage {
	SPAWNING(0) {
		@Override
		
		
		public Stage nextStage(Cell c) {
			return LIVING;
		}

		@Override
		public void apply(Cell c) {
			c.setAdditionalColor(RandomColor.randCol());
			c.setTransparency(0);
			c.toggle();
		}
	},
	LIVING(1) {
		@Override
		public Stage nextStage(Cell c) {			
			return LIVING;
		}

		@Override
		public void apply(Cell c) {
			c.setTransparency(-1);			
		}
	},
	DYING1(2) {
		@Override
		public Stage nextStage(Cell c) {			
			return DYING2;
		}

		@Override
		public void apply(Cell c) {
			c.setAdditionalColor(Color.GRAY);			
		}
	},
	DYING2(3) {
		@Override
		public Stage nextStage(Cell c) {			
			return DYING3;
		}

		@Override
		public void apply(Cell c) {
			c.setAdditionalColor(Color.DARK_GRAY);			
		}
	},
	DYING3(4) {
		@Override
		public Stage nextStage(Cell c) {
			c.toggle();
			return null;
		}

		@Override
		public void apply(Cell c) {
			c.setAdditionalColor(Color.RED);
		}
	};
	
	private int cur;
	
	private Stage(int n){
		cur = n;
	}
	
	private int which(){
		return cur;
	}
	
	public abstract Stage nextStage(Cell c);
	public abstract void apply(Cell c);
	
}
