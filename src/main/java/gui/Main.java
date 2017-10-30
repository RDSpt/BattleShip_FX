package gui;

import engine.board.Board;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	
	public static void main(String[] args) {
		//Board board = BoardTest.createTestBoard();
		Board board = Board.createStandardBoard();
		System.out.println(board.toString());
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/TableDesign.fxml"));
		primaryStage.setTitle("BattleShip_FX");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
}
