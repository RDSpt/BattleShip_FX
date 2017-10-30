package engine.board;

import com.google.common.collect.ImmutableMap;
import engine.player.Alliance;
import engine.ships.*;

import java.util.*;

import static engine.board.BoardUtils.*;

public class Board {
	
	private static Map<Map.Entry<Integer, Integer>, Tile> allyBoard;
	private static Map<Map.Entry<Integer, Integer>, Tile> naziBoard;
	private final Collection<Ship> alliesShips;
	private final Collection<Ship> nazisShips;
	
	private final engine.player.Allies allies;
	private final engine.player.Nazi nazi;
	private final engine.player.Player currentPlayer;
	
	public Board(final Builder builder) {
		
		this.allyBoard = createGameBoard();
		this.naziBoard = createRandomBoard();
		this.alliesShips = calculateShips(this.allyBoard, engine.player.Alliance.ALLIES);
		this.nazisShips = calculateShips(this.naziBoard, engine.player.Alliance.NAZI);
		this.allies = new engine.player.Allies(this);
		this.nazi = new engine.player.Nazi(this);
		this.currentPlayer = this.allies;
		//this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.allies, this.nazi);
	}
	
	public static Map<Map.Entry<Integer, Integer>, Tile> createRandomBoard() {
		
		final Map<Map.Entry<Integer, Integer>, Tile> tiles = new HashMap<>();
		for (int row = 0; row < TILES_PER_ROW; row++) {
			for (int col = 0; col < TILES_PER_COL; col++) {
				Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry<>(row, col);
				tiles.put(coordinate, Tile.createTile(row, col, Builder.naziBoardConfig.get(coordinate)));
			}
		}
		return ImmutableMap.copyOf(tiles);
	}
	
	public static ImmutableMap<Map.Entry<Integer, Integer>, Tile> createEmptyBoard(final Builder builder) {
		
		final Map<Map.Entry<Integer, Integer>, Tile> tiles = new HashMap<>();
		for (int row = 0; row < TILES_PER_ROW; row++) {
			for (int col = 0; col < TILES_PER_COL; col++) {
				Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry<>(row, col);
				tiles.put(coordinate, Tile.createTile(row, col, null));
			}
		}
		return ImmutableMap.copyOf(tiles);
	}
	
	public static Board createStandardBoard() {
		
		final Builder builder = new Builder();
		allyBoard = createEmptyBoard(builder);
		naziBoard = createEmptyBoard(builder);
		//Set Nazi Random location Ships
		boolean v1 = new Random().nextBoolean();
		builder.setNaziShip(Carrier.getInstance(generateRandomPosition(Ship.ShipType.CARRIER, v1), Alliance.NAZI, v1));
		v1 = new Random().nextBoolean();
		builder.setNaziShip(Battleship.getInstance(generateRandomPosition(Ship.ShipType.BATTLESHIP, v1), Alliance.NAZI, v1
		));
		v1 = new Random().nextBoolean();
		builder.setNaziShip(Cruise.getInstance(generateRandomPosition(Ship.ShipType.CRUISE, v1),Alliance.NAZI, v1));
		v1 = new Random().nextBoolean();
		builder.setNaziShip(Submarine.getInstance(generateRandomPosition(Ship.ShipType.SUBMARINE, v1), Alliance.NAZI,
				v1));
		v1 = new Random().nextBoolean();
		builder.setNaziShip(Destroyer.getInstance(generateRandomPosition(Ship.ShipType.DESTROYER, v1), Alliance.NAZI,
				v1));
		//User Location
		builder.setShip(Carrier.getInstance(new int[]{3, 3}, Alliance.ALLIES, true));
		builder.setShip(Battleship.getInstance(new int[]{6, 6}, Alliance.ALLIES, true));
		builder.setShip(Cruise.getInstance(new int[]{0, 0}, Alliance.ALLIES, true));
		builder.setShip(Submarine.getInstance(new int[]{1, 1}, Alliance.ALLIES, true));
		builder.setShip(Destroyer.getInstance(new int[]{2, 2}, Alliance.ALLIES, false));
		builder.setMoveMaker(engine.player.Alliance.ALLIES);
		return builder.build();
	}
	
	private static int[] generateRandomPosition(final Ship.ShipType ship, final boolean vertical) {
		
		int a;
		int b;
		do {
			a = ((int) (Math.random() * 10));
			b = ((int) (Math.random() * 10));
			
		} while (!shipLengthIsValid(a, b, ship, vertical));
		//} while (!isValidTile(a + ship.getShipSize(), b + ship.getShipSize(), ship, naziBoard));
		return new int[]{a, b};
	}
	
	private static boolean shipLengthIsValid(final int a, final int b, final Ship.ShipType ship, final boolean
			vertical) {
		
		int count = 0;
		for (int i = 0; i < ship.getShipSize(); i++) {
			if (vertical) {
				if (isValidTile(a + i, b, ship, naziBoard)) {
					count++;
				}
			}
			else {
				if (isValidTile(a, b + i, ship, naziBoard)) {
					count++;
				}
			}
		}
		return count == ship.getShipSize();
	}
	
	private Collection<Ship> calculateShips(final Map<Map.Entry<Integer, Integer>, Tile>
			                                        gameBoard,
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
	
	public static Map<Map.Entry<Integer, Integer>, Tile> createGameBoard(/*final Board.Builder builder*/) {
		
		final Map<Map.Entry<Integer, Integer>, Tile> tiles = new HashMap<>();
		for (int row = 0; row < TILES_PER_ROW; row++) {
			for (int col = 0; col < TILES_PER_COL; col++) {
				Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry<>(row, col);
				tiles.put(coordinate, Tile.createTile(row, col, Builder.allyBoardConfig.get(coordinate)));
			}
		}
		return ImmutableMap.copyOf(tiles);
	}
	
	public static boolean isValidTile(final int rowCoordinate,
	                                  final int colCoordinate,
	                                  final Ship.ShipType ship,
	                                  final Map<Map.Entry<Integer, Integer>, Tile> board) {
		
		for (int i = 0; i < ship.getShipSize(); i++) {
			if (rowCoordinate + i < TILES_PER_ROW && colCoordinate + i < TILES_PER_COL) {
				if (!getTile(board, coordinate(rowCoordinate, colCoordinate)).isTileOccupied() && //current is empty
						!getTile(board, coordinate(rowCoordinate - i, colCoordinate)).isTileOccupied() && //above is empty
						!getTile(board, coordinate(rowCoordinate, colCoordinate + i)).isTileOccupied() &&//right is empty
						!getTile(board, coordinate(rowCoordinate + i, colCoordinate)).isTileOccupied() &&//down is empty
						!getTile(board, coordinate(rowCoordinate, colCoordinate - i)).isTileOccupied()) {//left is empty
					return true;
				}
				
			}
			
		}
		return false;
	}
	
	public static Tile getTile(final Map<Map.Entry<Integer, Integer>, Tile> board,
	                           final Map.Entry<Integer, Integer> coordinate) {
		
		return board.get(coordinate);
	}
	
	public static Map<Map.Entry<Integer, Integer>, Tile> getAllyBoard() {
		
		return allyBoard;
	}
	
	public static Map<Map.Entry<Integer, Integer>, Tile> getNaziBoard() {
		
		return naziBoard;
	}
	
	@Override
	public String toString() {
		
		final StringBuilder builder = new StringBuilder();
		for (int row = 0; row < TILES_PER_ROW; row++) {
			for (int col = 0; col < TILES_PER_COL; col++) {
				Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry<>(row, col);
				final String                tileText   = this.allyBoard.get(coordinate).toString();
				builder.append(String.format("%3s", tileText));
			}
			builder.append("\n");
			
		}
		builder.append("\n");
		builder.append("\n");
		for (int row = 0; row < TILES_PER_ROW; row++) {
			for (int col = 0; col < TILES_PER_COL; col++) {
				Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry<>(row, col);
				final String                tileText   = this.naziBoard.get(coordinate).toString();
				builder.append(String.format("%3s", tileText));
			}
			builder.append("\n");
			
		}
		return builder.toString();
	}
	
	public static class Builder {
		
		static Map<Map.Entry<Integer, Integer>, Ship> allyBoardConfig;
		static Map<Map.Entry<Integer, Integer>, Ship> naziBoardConfig;
		engine.player.Alliance nextMoveMaker;
		
		public Builder() {
			
			this.allyBoardConfig = new HashMap<>();
			this.naziBoardConfig = new HashMap<>();
		}
		
		public Builder setNaziShip(final Ship ship) {
			
			for (int currentCoordinate = 0; currentCoordinate < ship.getShipPosition().size(); currentCoordinate += 2) {
				Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry<>(
						ship.getShipPosition().get(currentCoordinate),
						ship.getShipPosition().get(currentCoordinate + 1));
				if (isValidTile(ship.getShipPosition().get(currentCoordinate), ship.getShipPosition().get
						(currentCoordinate + 1), ship.getShipType(), naziBoard)) {
					this.naziBoardConfig.put(coordinate, ship);
					naziBoard = createRandomBoard();
					
				}
				else {
					throw new RuntimeException("Not a Valid Tile");
				}
			}
			return this;
		}
		
		public Builder setShip(final Ship ship) {
			
			for (int currentCoordinate = 0; currentCoordinate < ship.getShipPosition().size(); currentCoordinate += 2) {
				Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry<>(
						ship.getShipPosition().get(currentCoordinate),
						ship.getShipPosition().get(currentCoordinate + 1));
				if (isValidTile(ship.getShipPosition().get(currentCoordinate), ship.getShipPosition().get
						(currentCoordinate + 1), ship.getShipType(), allyBoard)) {
					//if (!getTile(coordinate).isTileOccupied()) {
					this.allyBoardConfig.put(coordinate, ship);
					//}
				}
				else {
					throw new RuntimeException("Not a Valid Tile");
				}
			}
			return this;
		}
		
		public Builder setMoveMaker(final engine.player.Alliance nextMoveMaker) {
			
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		
		@Override
		public String toString() {
			
			final StringBuilder builder = new StringBuilder();
			for (int row = 0; row < TILES_PER_ROW; row++) {
				for (int col = 0; col < TILES_PER_COL; col++) {
					Map.Entry<Integer, Integer> coordinate = new AbstractMap.SimpleEntry<>(row, col);
					final String                tileText   = toString();
					builder.append(String.format("%3s", tileText));
				}
				builder.append("\n");
				
			}
			return builder.toString();
		}
		
		public Board build() {
			
			return new Board(this);
		}
		
	}
}
