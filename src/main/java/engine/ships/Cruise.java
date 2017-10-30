package engine.ships;

import engine.player.Alliance;

public class Cruise extends Ship{
	
	private static Cruise allyInstance = null;
	private static Cruise naziInstance = null;
	
	public static Cruise getInstance(int[] shipPosition, Alliance shipAlliance, Boolean vertical) {
		
		if(allyInstance== null && shipAlliance.isAlly()){
			allyInstance = new Cruise(shipPosition, shipAlliance, vertical);
			return allyInstance;
		}
		if(naziInstance== null && shipAlliance.isNazi()){
			naziInstance = new Cruise(shipPosition, shipAlliance, vertical);
			return naziInstance;
		}
		return shipAlliance.isAlly() ? allyInstance : naziInstance;
	}
	
	private Cruise(int[] initialCoordinate, Alliance shipAlliance, Boolean vertical) {
		super(ShipType.CRUISE, initialCoordinate, shipAlliance, vertical);
	}
	
}
