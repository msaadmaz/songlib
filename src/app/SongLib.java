package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import structures.SongList;
import view.ListController;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class SongLib extends Application {
	
	public void start(Stage primaryStage) throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(
		getClass().getResource("/view/gui.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		ListController ListController =
				loader.getController();
		ListController.start(primaryStage);
		
		Scene scene = new Scene(root, 1000, 1000);
		primaryStage.setScene(scene);
		primaryStage.show(); 
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

}
