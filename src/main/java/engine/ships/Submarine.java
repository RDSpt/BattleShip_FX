package engine.ships;

import engine.player.Alliance;

public class Submarine extends Ship{
	
	private static Submarine allyInstance = null;
	private static Submarine naziInstance = null;
	
	public static Submarine getInstance(int[] shipPosition, Alliance shipAlliance, Boolean vertical) {
		
		if(allyInstance== null && shipAlliance.isAlly()){
			allyInstance = new Submarine(shipPosition, shipAlliance, vertical);
			return allyInstance;
		}
		if(naziInstance== null && shipAlliance.isNazi()){
			naziInstance = new Submarine(shipPosition, shipAlliance, vertical);
			return naziInstance;
		}
		return shipAlliance.isAlly() ? allyInstance : naziInstance;
	}
	
	private Submarine(int[] initialCoordinate, Alliance shipAlliance, Boolean vertical) {
		super(ShipType.SUBMARINE, initialCoordinate, shipAlliance, vertical);
	}
	
}
