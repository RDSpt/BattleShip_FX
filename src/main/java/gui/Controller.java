package gui;

import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;

public class Controller {
	
	@FXML
	private MenuBar MenuBar;
	@FXML
	private Pane MainPane;
	
	public static Pane MainPANE;
	public static MenuBar MenuBAR;
	
	public void initialize() {
		
		MainPANE = MainPane;
		MenuBAR = MenuBar;
		Table.get().show();
	}
	
}
