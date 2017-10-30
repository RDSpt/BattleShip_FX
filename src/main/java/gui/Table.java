package gui;

import engine.board.*;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;

import java.util.*;
import java.util.List;

import static gui.Controller.MainPANE;
import static gui.Controller.MenuBAR;
import static javafx.scene.input.MouseEvent.MOUSE_ENTERED;
import static javafx.scene.input.MouseEvent.MOUSE_ENTERED_TARGET;

public class Table {
	
	BoardPanel allyPanel;
	BoardPanel naziPanel;
	private Map<Map.Entry<Integer, Integer>, Tile> allyBoard = Board.createGameBoard();
	private Map<Map.Entry<Integer, Integer>, Tile> naziBoard = Board.createRandomBoard();
	
	private static final Table INSTANCE = new Table();
	
	private Table() {
		
		createTableMenuBar();
		allyPanel = new BoardPanel(allyBoard);
		naziPanel = new BoardPanel(naziBoard);
		MainPANE.getChildren().add(allyPanel);
		MainPANE.getChildren().add(new Separator(Orientation.VERTICAL));
		MainPANE.getChildren().add(naziPanel);
		
	}
	
	public void createTableMenuBar() {
		
		MenuBAR.getMenus().add(createFileMenu());
	}
	
	//Creates File Menu
	private Menu createFileMenu() {
		
		final Menu     fileMenu = new Menu("File");
		final MenuItem openPGN  = new MenuItem("Load PGN FIle");
		openPGN.setOnAction(event -> System.out.println("Open PGN file"));
		final MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(event -> System.exit(0));
		fileMenu.getItems().add(openPGN);
		fileMenu.getItems().add(exitMenuItem);
		return fileMenu;
	}
	
	//Create Preferences Menu
	private Menu createPreferencesMenu() {
		
		final Menu     preferencesMenu   = new Menu("Preferences");
		final MenuItem flipBoardMenuItem = new MenuItem("Flip Board");
		flipBoardMenuItem.setOnAction(event -> {
			System.out.println("Test");
		});
		//Add to Menu
		preferencesMenu.getItems().add(flipBoardMenuItem);
		preferencesMenu.getItems().add(new SeparatorMenuItem());
		return preferencesMenu;
	}
	
	public static Table get() {
		
		return INSTANCE;
	}
	
	public void show() {
		
		/*Table.get().getMoveLog().clear();
		Table.get().getGameHistoryPane().redo(checkersBoard, Table.get().getMoveLog());
		Table.get().getTakenPiecesPanel().redo(Table.get().getMoveLog());
		Table.get().getBoardPanel().drawBoard(Table.get().getGameBoard());*/
	}
	
	private class BoardPanel extends GridPane {
		
		final List<TilePanel> boardTiles;
		TilePanel tilePanel;
		GridPane grid = new GridPane();
		
		private BoardPanel(Map<Map.Entry<Integer, Integer>, Tile> board) {
			
			this.boardTiles = new ArrayList<>();
			int tileNum = 0;
			for (int r = 0; r < BoardUtils.TILES_PER_COL; r++) {
				for (int c = 0; c < BoardUtils.TILES_PER_ROW; c++) {
					tilePanel = new TilePanel(this, tileNum, r + 1, c + 1);
					tilePanel.addMouseListener(board);
					grid.setAlignment(Pos.CENTER);
					grid.add(tilePanel, c, r);
					this.boardTiles.add(tilePanel);
					tileNum++;
				}
				setPrefSize(400, 350);
				
			}
			add(grid, 1, 2);
			
		}
		
		/*public void drawBoard(final Board board) {
			
			getChildren().removeAll();
			getChildren().clear();
			for (TilePanel tilePanel : board.boardTiles)) {
				tilePanel.getChildren().clear();
				tilePanel.drawTile(board);
				getChildren().add(tilePanel);
			}
		}*/
		
	}
	
	enum PlayerType {
		HUMAN,
		COMPUTER;
	}
	
	private class TilePanel extends TilePane {
		
		private final int tileId;
		private final Map.Entry<Integer, Integer> coordinate;
		
		TilePanel(final BoardPanel boardPanel,
		          final int tileId, final int row, final int col) {
			
			this.tileId = tileId;
			this.coordinate = BoardUtils.coordinate(row, col);
			setMinSize(60, 60);
			assignTileColor();
			//addMouseListener();
			
			/*assignTilePieceIcon(allyBoard);
			assignTilePieceIcon(naziBoard);*/
		}
		
		public void addMouseListener(Map<Map.Entry<Integer, Integer>, Tile> board) {
			
			setOnMouseEntered(event -> {
				if (board.get(this.coordinate) != null && board == naziBoard)
					if (board.get(this.coordinate).isTileOccupied() && !board.get(this.coordinate).isShootted()) {
						Image image = new Image("sight.png");
						setCursor(new ImageCursor(image,
								16, 16));
					}
					else {
						setCursor(Cursor.DEFAULT);
					}
			});
			setOnMouseExited(event -> setCursor(Cursor.DEFAULT));
			setOnMouseClicked(event -> {
				if (isLeftMouseButton(event)) {
					//first click
					System.out.println("Left Click " + this.coordinate);
					System.out.println("X " + event.getX());
					System.out.println("Y " + event.getY());
				
				}
				else if (isRightMouseButton(event)) {
					//reset click
					System.out.println("Right Click " + this.coordinate);
					/*sourceTile = null;
					destinationTile = null;
					humanMovedPiece = null;*/
					
					//leftMouse
					/*if (sourceTile == null) { //not clicked yet
						sourceTile = checkersBoard.getTile(tileId);//select Tile
						humanMovedPiece = sourceTile.getPiece();//get piece on tile
						if (humanMovedPiece == null) {//no piece selected
							sourceTile = null;//reset
						}
					}
					else { //piece selected
						destinationTile = checkersBoard.getTile(tileId); //select destination
						final Move move = MoveFactory.createMove(checkersBoard, sourceTile.getTileCoordinate(),
								destinationTile.getTileCoordinate());
						final MoveTransition transition = checkersBoard.currentPlayer().makeMove(move);
						if (transition.getMoveStatus().isDone()) {
							checkersBoard = transition.getTransitionBoard();
							moveLog.addMove(move);
						}
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;
					}
					*/
				}
				/*Platform.runLater(() -> {
					if (checkersBoard.getBlackPieces().size() != 0 || checkersBoard.getWhitePieces().size() != 0) {
						takenPiecesPanel.redo(moveLog);
						if (gameSetup.isAIPlayer(checkersBoard.currentPlayer())) {
							Table.get().moveMadeUpdate(PlayerType.HUMAN);
						}
						allyPanel.drawBoard(checkersBoard);
					}
					else {
						String winner = checkersBoard.getBlackPieces().size() == 0 ? "White Player" : "Black Player";
						Alert  alert  = new Alert(Alert.AlertType.NONE);
						alert.setContentText(winner + " just won the game!");
						alert.show();
					}
				});*/
			});
		}
		
		private boolean isRightMouseButton(MouseEvent event) {
			
			if (event.getButton() == MouseButton.SECONDARY)
				return true;
			else
				return false;
		}
		
		private boolean isLeftMouseButton(MouseEvent event) {
			
			if (event.getButton() == MouseButton.PRIMARY)
				return true;
			else
				return false;
		}
		
		private void assignTilePieceIcon(final Map<Map.Entry<Integer, Integer>, Tile> board) {
			
			this.getChildren().removeAll();
			Tile tile = board.get(this.coordinate);
			if (board.get(this.coordinate).isTileOccupied()) {
				final Image image = new Image("/Graphics/" +
						tile.getShip().getShipAlliance().toString().substring(0, 1) +
						tile.getShip().toString() + ".png", 50, 50, true, true);
				final ImageView imageView = new ImageView(image);
				setAlignment(Pos.CENTER);
				this.getChildren().add(imageView);
			}
			
		}
		
		private void assignTileColor() {
			
			Background sea = new Background(new BackgroundImage(new Image("sea.jpg", 60, 60, true, true),
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize
					.DEFAULT));
			setBorder(new Border(new BorderStroke(Paint.valueOf("#bababa"), BorderStrokeStyle.SOLID, CornerRadii
					.EMPTY, BorderWidths.DEFAULT)));
			setBackground(sea);
			
		}
		
		public void drawTile(final Board board) {
			
			assignTileColor();
			//assignTilePieceIcon(board);
		}
	}
	
}
