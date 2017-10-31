package gui;

import engine.board.Board;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Main extends Application {
	
	public static void main(String[] args) {
		//Board board = BoardTest.createTestBoard();
		Board board = Board.createStandardBoard();
		System.out.println(board.toString());
		launch(args);
		
		/*String variableName = Board.class.getSimpleName().toLowerCase();
		String name         = variableName + 1;
		//Board.class.getField(name) = "banana";
		ArrayList<String> a = new ArrayList<String>();
		for(Field field: Board.class.getFields()){
			System.out.println(field.get(Object));
			//field =  "banana";
		}
		//name = props.gerParameter(name)
		System.out.println(name);*/
	}
	

	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		Parent root = FXMLLoader.load(getClass().getResource("/TableDesign.fxml"));
		primaryStage.setTitle("BattleShip_FX");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
}
