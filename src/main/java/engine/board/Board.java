package engine.board;

import com.google.common.collect.*;
import com.google.common.collect.Table;
import engine.player.*;
import engine.ships.Ship;

import java.util.*;

public class Board {
	
	private final Table<Integer,Integer, engine.board.Tile> gameBoard;
	private final Collection<Ship> alliesShips;
	private final Collection<Ship> nazisShips;
	
	private final engine.player.Allies allies;
	private final engine.player.Nazi nazi;
	private final engine.player.Player currentPlayer;
	
	private Board(final Builder builder) {
		
		this.gameBoard = createGameBoard(builder);
		this.alliesShips = calculateShips(this.gameBoard, engine.player.Alliance.ALLIES);
		this.nazisShips = calculateShips(this.gameBoard, engine.player.Alliance.NAZI);
		this.allies = new engine.player.Allies(this);
		this.nazi = new engine.player.Nazi(this);
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.allies, this.nazi);
		
	}
	
	private Collection<Ship> calculateShips(final Table<Integer,Integer, engine.board.Tile> gameBoard,
	                                                final engine.player.Alliance alliance) {
		
		final List<Ship> activePieces = new ArrayList<>();
		for (final engine.board.Tile tile : gameBoard.values()) {
			if (tile.isTileOccupied()) {
				final Ship ship = tile.getShip();
				if (ship.getShipAlliance() == alliance) {
					activePieces.add(ship);
				}
			}
		}
		return activePieces;
	}
	
	private static Table<Integer,Integer, engine.board.Tile> createGameBoard(final Board.Builder builder) {
		
		final Table<Integer,Integer, engine.board.Tile> tiles = null;
		for (int row = 0; row < engine.board.BoardUtils.TILES_PER_ROW; row++) {
			for (int col = 0; col < engine.board.BoardUtils.TILES_PER_COL; col++) {
				tiles.put(row, col, engine.board.Tile.createTile(row,col, builder.boardConfig.get( row, col)));
			}
		}
		return ImmutableTable.copyOf(tiles);
	}
	
	public static class Builder {
		Table<Integer,Integer,Ship> boardConfig = (Table<Integer,Integer,Ship>) new HashMap<>();
		engine.player.Alliance nextMoveMaker;
		
		public Builder() {
			
		}
		
		public Builder setPiece(final Ship ship) {
			
			this.boardConfig.put(ship.getShipPosition()[0],ship.getShipPosition()[1], ship);
			return this;
		}
		
		public Builder setMoveMaker(final engine.player.Alliance nextMoveMaker) {
			
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		public Board build() {
			
			return new Board(this);
		}
		
	}
}
