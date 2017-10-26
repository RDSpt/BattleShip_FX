package engine.player;

import engine.player.Allies;

public enum Alliance {
	ALLIES {
		public boolean isNazi() {
			
			return false;
		}
		
		public boolean isAlly() {
			
			return true;
		}
		
		public Player choosePlayer(Allies ally, Nazi nazi) {
			
			return ally;
		}
	},
	NAZI {
		public boolean isNazi() {
			
			return true;
		}
		
		public boolean isAlly() {
			
			return false;
		}
		
		public Player choosePlayer(Allies ally, Nazi nazi) {
			
			return nazi;
		}
	};
	
	public abstract boolean isNazi();
	public abstract boolean isAlly();
	public abstract Player choosePlayer(final Allies ally, final Nazi nazi);
}
