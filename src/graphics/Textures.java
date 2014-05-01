package src.graphics;

import com.threed.jpct.Texture;

public enum Textures {
	BLOCK{

		@Override
		public Texture texturize() {
	

			return null;
		}
		
	}, WALL{

		@Override
		public Texture texturize() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}, SKY{

		@Override
		public Texture texturize() {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
	
	abstract public Texture texturize();

}
