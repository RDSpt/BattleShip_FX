package engine.board;

import engine.player.Alliance;

import java.util.Map;

public class Shot {
	
	private int[] tileCoordinate;
	private boolean missed;
	
	public Shot(int row, int col, Alliance alliance) {
		this.tileCoordinate = new int[]{row, col};
		this.missed = accurateShot(allianceBoard(alliance));
	}
	
	private Map<Map.Entry<Integer, Integer>, Tile> allianceBoard(final Alliance alliance){
		return alliance.isAlly()? Board.getAllyBoard(): Board.getNaziBoard();
		
	}
	
	private boolean accurateShot(final Map<Map.Entry<Integer, Integer>, Tile> board){
		
		Map.Entry<Integer, Integer> coordinate = BoardUtils.coordinate(tileCoordinate[0], tileCoordinate[1]);
		Tile tileShot = Board.getTile(board, coordinate);
		return tileShot.isTileOccupied() && !tileShot.isShootted();
	}
	
	
	
	
}
