package engine.ships;

import engine.board.Board;
import engine.player.Alliance;

public class Carrier extends Ship{
	
	private static Carrier allyInstance = null;
	private static Carrier naziInstance = null;
	
	public static Carrier getInstance(int[] shipPosition, Alliance shipAlliance, Boolean vertical) {
		
		if(allyInstance== null && shipAlliance.isAlly()){
			allyInstance = new Carrier(shipPosition, shipAlliance, vertical);
			return allyInstance;
		}
		if(naziInstance== null && shipAlliance.isNazi()){
			naziInstance = new Carrier(shipPosition, shipAlliance, vertical);
			return naziInstance;
		}
		return shipAlliance.isAlly() ? allyInstance : naziInstance;
	}
	
	private Carrier(int[] initialCoordinate, Alliance shipAlliance, Boolean vertical) {
		super(ShipType.CARRIER, initialCoordinate, shipAlliance, vertical);
	}
	
}
